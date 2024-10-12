package com.mck.backend.service;

import com.mck.backend.domain.Department;
import com.mck.backend.domain.Employee;
import com.mck.backend.model.DepartmentDTO;
import com.mck.backend.repos.DepartmentRepository;
import com.mck.backend.repos.EmployeeRepository;
import com.mck.backend.util.NotFoundException;
import com.mck.backend.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DepartmentService {

  private final DepartmentRepository departmentRepository;
  private final EmployeeRepository employeeRepository;

  public DepartmentService(final DepartmentRepository departmentRepository,
      final EmployeeRepository employeeRepository) {
    this.departmentRepository = departmentRepository;
    this.employeeRepository = employeeRepository;
  }

  public List<DepartmentDTO> findAll() {
    final List<Department> departments = departmentRepository.findAll(Sort.by("id"));
    return departments.stream()
        .map(department -> mapToDTO(department, new DepartmentDTO()))
        .toList();
  }

  public DepartmentDTO get(final Long id) {
    return departmentRepository.findById(id)
        .map(department -> mapToDTO(department, new DepartmentDTO()))
        .orElseThrow(NotFoundException::new);
  }

  public Long create(final DepartmentDTO departmentDTO) {
    final Department department = new Department();
    mapToEntity(departmentDTO, department);
    return departmentRepository.save(department).getId();
  }

  public void update(final Long id, final DepartmentDTO departmentDTO) {
    final Department department = departmentRepository.findById(id)
        .orElseThrow(NotFoundException::new);
    mapToEntity(departmentDTO, department);
    departmentRepository.save(department);
  }

  public void delete(final Long id) {
    departmentRepository.deleteById(id);
  }

  private DepartmentDTO mapToDTO(final Department department, final DepartmentDTO departmentDTO) {
    departmentDTO.setId(department.getId());
    departmentDTO.setName(department.getName());
    return departmentDTO;
  }

  private Department mapToEntity(final DepartmentDTO departmentDTO, final Department department) {
    department.setName(departmentDTO.getName());
    return department;
  }

  public ReferencedWarning getReferencedWarning(final Long id) {
    final ReferencedWarning referencedWarning = new ReferencedWarning();
    final Department department = departmentRepository.findById(id)
        .orElseThrow(NotFoundException::new);
    final Employee departmentEmployee = employeeRepository.findFirstByDepartment(department);
    if (departmentEmployee != null) {
      referencedWarning.setKey("department.employee.department.referenced");
      referencedWarning.addParam(departmentEmployee.getId());
      return referencedWarning;
    }
    return null;
  }

}
