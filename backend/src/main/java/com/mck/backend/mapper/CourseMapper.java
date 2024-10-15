package com.mck.backend.mapper;

import com.mck.backend.domain.Course;
import com.mck.backend.model.CourseDTO;
import com.mck.backend.request.CreateCourseRequest;
import com.mck.backend.request.UpdateCourseRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {

  CourseDTO toDTO(CreateCourseRequest request);

  @Mapping(source = "id", target = "id")
  CourseDTO toDTO(Long id, UpdateCourseRequest request);

  CourseDTO toDTO(Course course);

  Course toEntity(CourseDTO courseDTO);

}
