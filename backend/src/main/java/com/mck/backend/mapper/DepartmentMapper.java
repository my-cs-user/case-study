package com.mck.backend.mapper;

import com.mck.backend.domain.Department;
import com.mck.backend.model.DepartmentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

	DepartmentDTO toDTO(Department department);

	Department toEntity(DepartmentDTO departmentDTO);

}
