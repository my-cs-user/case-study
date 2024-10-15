package com.mck.backend.mapper;

import com.mck.backend.domain.Department;
import com.mck.backend.model.DepartmentDTO;
import com.mck.backend.request.CreateDepartmentRequest;
import com.mck.backend.request.UpdateDepartmentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

  DepartmentDTO toDTO(CreateDepartmentRequest request);

  @Mapping(source = "id", target = "id")
  DepartmentDTO toDTO(Long id, UpdateDepartmentRequest request);

  DepartmentDTO toDTO(Department department);

  Department toEntity(DepartmentDTO departmentDTO);

}
