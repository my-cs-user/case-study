package com.mck.backend.service;

import static org.apache.commons.lang3.StringUtils.isBlank;

import com.mck.backend.domain.Department;
import com.mck.backend.domain.Employee;
import com.mck.backend.mapper.EmployeeMapper;
import com.mck.backend.model.EmployeeDTO;
import com.mck.backend.repos.DepartmentRepository;
import com.mck.backend.repos.EmployeeRepository;
import com.mck.backend.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;

	private final DepartmentRepository departmentRepository;

	private final EmployeeMapper employeeMapper;

	public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository,
			EmployeeMapper employeeMapper) {
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
		this.employeeMapper = employeeMapper;
	}

	public EmployeeDTO get(Long id) {
		return employeeRepository.findById(id).map(this::mapToDTO).orElseThrow(NotFoundException::new);
	}

	public Long create(EmployeeDTO employeeDTO) {
		return employeeRepository.save(mapToEntity(employeeDTO)).getId();
	}

	public void update(Long id, EmployeeDTO employeeDTO) {
		Employee employee = employeeRepository.findById(id).orElseThrow(NotFoundException::new);
		employeeRepository.save(mapToEntity(employeeDTO));
	}

	public void delete(Long id) {
		employeeRepository.deleteById(id);
	}

	public Page<EmployeeDTO> findByDepartmentId(Long departmentId, Pageable pageable, String searchText) {
		Department department = departmentRepository.findById(departmentId).orElseThrow(NotFoundException::new);
		Page<Employee> employees;
		if (isBlank(searchText)) {
			employees = employeeRepository.findByDepartment(department, pageable);
		}
		else {
			employees = employeeRepository.findByDepartmentAndSearchText(department, searchText, pageable);
		}
		return employees.map(this::mapToDTO);
	}

	private EmployeeDTO mapToDTO(Employee employee) {
		return employeeMapper.toDTO(employee);
	}

	private Employee mapToEntity(EmployeeDTO employeeDTO) {
		return employeeMapper.toEntity(employeeDTO, departmentRepository);
	}

}
