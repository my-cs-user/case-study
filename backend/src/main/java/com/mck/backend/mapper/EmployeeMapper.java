package com.mck.backend.mapper;

import com.mck.backend.domain.Department;
import com.mck.backend.domain.Employee;
import com.mck.backend.exception.NotFoundException;
import com.mck.backend.model.EmployeeDTO;
import com.mck.backend.repository.DepartmentRepository;
import com.mck.backend.request.CreateEmployeeRequest;
import com.mck.backend.request.UpdateEmployeeRequest;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

  EmployeeDTO toDTO(CreateEmployeeRequest request);

  @Mapping(source = "id", target = "id")
  EmployeeDTO toDTO(Long id, UpdateEmployeeRequest request);

  @Mapping(source = "department", target = "department", qualifiedByName = "departmentToId")
  EmployeeDTO toDTO(Employee employee);

  @Mapping(source = "department", target = "department", qualifiedByName = "idToDepartment")
  Employee toEntity(EmployeeDTO employeeDTO, @Context DepartmentRepository departmentRepository);

  @Named("departmentToId")
  default Long departmentToId(Department department) {
    return department != null ? department.getId() : null;
  }

  @Named("idToDepartment")
  default Department idToDepartment(Long departmentId,
      @Context DepartmentRepository departmentRepository) {
    return departmentId != null ? departmentRepository.findById(departmentId)
        .orElseThrow(() -> new NotFoundException("Department not found")) : null;
  }

}
