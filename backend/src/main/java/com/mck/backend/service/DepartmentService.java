package com.mck.backend.service;

import com.mck.backend.domain.Department;
import com.mck.backend.domain.Employee;
import com.mck.backend.mapper.DepartmentMapper;
import com.mck.backend.model.DepartmentDTO;
import com.mck.backend.repository.DepartmentRepository;
import com.mck.backend.repository.EmployeeRepository;
import com.mck.backend.util.NotFoundException;
import com.mck.backend.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

  private final DepartmentRepository departmentRepository;

  private final EmployeeRepository employeeRepository;

  private final DepartmentMapper departmentMapper;

  public DepartmentService(DepartmentRepository departmentRepository,
      EmployeeRepository employeeRepository,
      DepartmentMapper departmentMapper) {
    this.departmentRepository = departmentRepository;
    this.employeeRepository = employeeRepository;
    this.departmentMapper = departmentMapper;
  }

  public List<DepartmentDTO> findAll() {
    List<Department> departments = departmentRepository.findAll(Sort.by("id"));
    return departments.stream().map(this::mapToDTO).toList();
  }

  public DepartmentDTO get(Long id) {
    return departmentRepository.findById(id).map(this::mapToDTO)
        .orElseThrow(NotFoundException::new);
  }

  public Long create(DepartmentDTO departmentDTO) {
    return departmentRepository.save(mapToEntity(departmentDTO)).getId();
  }

  public void update(DepartmentDTO departmentDTO) {
    departmentRepository.findById(departmentDTO.id()).orElseThrow(NotFoundException::new);
    departmentRepository.save(mapToEntity(departmentDTO));
  }

  public void delete(Long id) {
    departmentRepository.deleteById(id);
  }

  private DepartmentDTO mapToDTO(Department department) {
    return departmentMapper.toDTO(department);
  }

  private Department mapToEntity(DepartmentDTO departmentDTO) {
    return departmentMapper.toEntity(departmentDTO);
  }

  public ReferencedWarning getReferencedWarning(Long id) {
    ReferencedWarning referencedWarning = new ReferencedWarning();
    Department department = departmentRepository.findById(id).orElseThrow(NotFoundException::new);
    Employee departmentEmployee = employeeRepository.findFirstByDepartment(department);
    if (departmentEmployee != null) {
      referencedWarning.setKey("department.employee.department.referenced");
      referencedWarning.addParam(departmentEmployee.getId());
      return referencedWarning;
    }
    return null;
  }

}
