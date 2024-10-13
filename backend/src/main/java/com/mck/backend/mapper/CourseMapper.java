package com.mck.backend.mapper;

import com.mck.backend.domain.Course;
import com.mck.backend.model.CourseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CourseMapper {

	CourseDTO toDTO(Course course);

	Course toEntity(CourseDTO courseDTO);

}
