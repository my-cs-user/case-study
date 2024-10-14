package com.mck.backend.mapper;

import com.mck.backend.app.request.CreateStudentRequest;
import com.mck.backend.app.request.UpdateStudentRequest;
import com.mck.backend.domain.Course;
import com.mck.backend.domain.Student;
import com.mck.backend.model.StudentDTO;
import com.mck.backend.repository.CourseRepository;
import com.mck.backend.util.NotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = CourseMapper.class)
public interface StudentMapper {

	StudentDTO toDTO(CreateStudentRequest request);

	@Mapping(source = "id", target = "id")
	StudentDTO toDTO(Long id, UpdateStudentRequest request);

	@Mapping(source = "courses", target = "courses", qualifiedByName = "coursesToIds")
	StudentDTO toDTO(Student student);

	@Mapping(source = "courses", target = "courses", qualifiedByName = "idsToCourses")
	Student toEntity(StudentDTO studentDTO, @Context CourseRepository courseRepository);

	@Named("coursesToIds")
	default List<Long> coursesToIds(Set<Course> courses) {
		return courses != null ? courses.stream().map(Course::getId).toList() : null;
	}

	@Named("idsToCourses")
	default Set<Course> idsToCourses(List<Long> courseIds, @Context CourseRepository courseRepository) {
		if (courseIds == null) {
			return null;
		}
		List<Course> courses = courseRepository.findAllById(courseIds);
		if (courses.size() != courseIds.size()) {
			throw new NotFoundException("One of the courses not found");
		}
		return new HashSet<>(courses);
	}

}
