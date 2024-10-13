package com.mck.backend.service;

import com.mck.backend.domain.Course;
import com.mck.backend.mapper.CourseMapper;
import com.mck.backend.model.CourseDTO;
import com.mck.backend.repository.CourseRepository;
import com.mck.backend.repository.StudentRepository;
import com.mck.backend.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CourseService {

	private final CourseRepository courseRepository;

	private final StudentRepository studentRepository;

	private final CourseMapper courseMapper;

	public CourseService(CourseRepository courseRepository, StudentRepository studentRepository,
			CourseMapper courseMapper) {
		this.courseRepository = courseRepository;
		this.studentRepository = studentRepository;
		this.courseMapper = courseMapper;
	}

	public List<CourseDTO> findAll() {
		List<Course> courses = courseRepository.findAll(Sort.by("id"));
		return courses.stream().map(course -> mapToDTO(course, new CourseDTO())).toList();
	}

	public CourseDTO get(Long id) {
		return courseRepository.findById(id)
			.map(course -> mapToDTO(course, new CourseDTO()))
			.orElseThrow(NotFoundException::new);
	}

	public Long create(CourseDTO courseDTO) {
		return courseRepository.save(mapToEntity(courseDTO)).getId();
	}

	public void update(Long id, CourseDTO courseDTO) {
		Course course = courseRepository.findById(id).orElseThrow(NotFoundException::new);
		courseRepository.save(mapToEntity(courseDTO));
	}

	public void delete(Long id) {
		Course course = courseRepository.findById(id).orElseThrow(NotFoundException::new);
		// remove many-to-many relations at owning side
		studentRepository.findAllByCourses(course).forEach(student -> student.getCourses().remove(course));
		courseRepository.delete(course);
	}

	private CourseDTO mapToDTO(Course course, CourseDTO courseDTO) {
		courseDTO.setId(course.getId());
		courseDTO.setName(course.getName());
		return courseDTO;
	}

	private Course mapToEntity(CourseDTO courseDTO) {
		return courseMapper.toEntity(courseDTO);
	}

}
