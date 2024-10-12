package com.mck.backend.service;

import com.mck.backend.domain.Department;
import com.mck.backend.domain.Employee;
import com.mck.backend.model.EmployeeDTO;
import com.mck.backend.repos.DepartmentRepository;
import com.mck.backend.repos.EmployeeRepository;
import com.mck.backend.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final DepartmentRepository departmentRepository;

  public EmployeeService(final EmployeeRepository employeeRepository,
      final DepartmentRepository departmentRepository) {
    this.employeeRepository = employeeRepository;
    this.departmentRepository = departmentRepository;
  }

  public List<EmployeeDTO> findAll() {
    final List<Employee> employees = employeeRepository.findAll(Sort.by("id"));
    return employees.stream().map(employee -> mapToDTO(employee, new EmployeeDTO())).toList();
  }

  public EmployeeDTO get(final Long id) {
    return employeeRepository.findById(id).map(employee -> mapToDTO(employee, new EmployeeDTO()))
        .orElseThrow(NotFoundException::new);
  }

  public Long create(final EmployeeDTO employeeDTO) {
    final Employee employee = new Employee();
    mapToEntity(employeeDTO, employee);
    return employeeRepository.save(employee).getId();
  }

  public void update(final Long id, final EmployeeDTO employeeDTO) {
    final Employee employee = employeeRepository.findById(id).orElseThrow(NotFoundException::new);
    mapToEntity(employeeDTO, employee);
    employeeRepository.save(employee);
  }

  public void delete(final Long id) {
    employeeRepository.deleteById(id);
  }

  public Page<EmployeeDTO> findByDepartmentId(Long departmentId, Pageable pageable) {
    Department department = departmentRepository.findById(departmentId)
        .orElseThrow(NotFoundException::new);
    Page<Employee> employees = employeeRepository.findByDepartment(department, pageable);
    return employees.map(employee -> mapToDTO(employee, new EmployeeDTO()));
  }

  private EmployeeDTO mapToDTO(final Employee employee, final EmployeeDTO employeeDTO) {
    employeeDTO.setId(employee.getId());
    employeeDTO.setName(employee.getName());
    employeeDTO.setSurname(employee.getSurname());
    employeeDTO.setSalary(employee.getSalary());
    employeeDTO.setEmail(employee.getEmail());
    employeeDTO.setPhone(employee.getPhone());
    employeeDTO.setDepartment(
        employee.getDepartment() == null ? null : employee.getDepartment().getId());
    return employeeDTO;
  }

  private Employee mapToEntity(final EmployeeDTO employeeDTO, final Employee employee) {
    employee.setName(employeeDTO.getName());
    employee.setSurname(employeeDTO.getSurname());
    employee.setSalary(employeeDTO.getSalary());
    employee.setEmail(employeeDTO.getEmail());
    employee.setPhone(employeeDTO.getPhone());
    final Department department = employeeDTO.getDepartment() == null ? null
        : departmentRepository.findById(employeeDTO.getDepartment())
            .orElseThrow(() -> new NotFoundException("department not found"));
    employee.setDepartment(department);
    return employee;
  }

}
