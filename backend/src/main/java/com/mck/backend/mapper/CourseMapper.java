package com.mck.backend.mapper;

import com.mck.backend.domain.Course;
import com.mck.backend.model.CourseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {

	CourseDTO toDTO(Course course);

	Course toEntity(CourseDTO courseDTO);

}
