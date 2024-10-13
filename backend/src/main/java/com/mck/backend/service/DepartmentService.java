package com.mck.backend.service;

import com.mck.backend.domain.Department;
import com.mck.backend.domain.Employee;
import com.mck.backend.mapper.DepartmentMapper;
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
  private final DepartmentMapper departmentMapper;

  public DepartmentService(DepartmentRepository departmentRepository,
      EmployeeRepository employeeRepository, DepartmentMapper departmentMapper) {
    this.departmentRepository = departmentRepository;
    this.employeeRepository = employeeRepository;
    this.departmentMapper = departmentMapper;
  }

  public DepartmentDTO get(Long id) {
    return departmentRepository.findById(id)
        .map(this::mapToDTO)
        .orElseThrow(NotFoundException::new);
  }

  public Long create(DepartmentDTO departmentDTO) {
    return departmentRepository.save(mapToEntity(departmentDTO)).getId();
  }

  public void update(Long id, DepartmentDTO departmentDTO) {
    final Department department = departmentRepository.findById(id)
        .orElseThrow(NotFoundException::new);
    departmentRepository.save(mapToEntity(departmentDTO));
  }

  public void delete(Long id) {
    departmentRepository.deleteById(id);
  }

  private DepartmentDTO mapToDTO(Department department) {
    return departmentMapper.toDTO(department);
  }

  private Department mapToEntity(DepartmentDTO departmentDTO) {
    return departmentMapper.fromDTO(departmentDTO);
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
