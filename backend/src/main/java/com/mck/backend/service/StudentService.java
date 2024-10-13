package com.mck.backend.service;

import static org.apache.commons.lang3.StringUtils.isBlank;

import com.mck.backend.domain.Course;
import com.mck.backend.domain.Student;
import com.mck.backend.mapper.StudentMapper;
import com.mck.backend.model.StudentDTO;
import com.mck.backend.repos.CourseRepository;
import com.mck.backend.repos.StudentRepository;
import com.mck.backend.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class StudentService {

	private final StudentRepository studentRepository;

	private final CourseRepository courseRepository;

	private final StudentMapper studentMapper;

	public StudentService(StudentRepository studentRepository, CourseRepository courseRepository,
			StudentMapper studentMapper) {
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
		this.studentMapper = studentMapper;
	}

	public Page<StudentDTO> findByCourseId(Long courseId, Pageable pageable, String searchText) {
		Course course = courseRepository.findById(courseId).orElseThrow(NotFoundException::new);
		Page<Student> students;
		if (isBlank(searchText)) {
			students = studentRepository.findAllByCourses(course, pageable);
		}
		else {
			students = studentRepository.findAllByCoursesAndSearchText(course, pageable, searchText);
		}
		return students.map(this::mapToDTO);
	}

	public StudentDTO get(Long id) {
		return studentRepository.findById(id).map(this::mapToDTO).orElseThrow(NotFoundException::new);
	}

	public Long create(StudentDTO studentDTO) {
		return studentRepository.save(mapToEntity(studentDTO)).getId();
	}

	public void update(Long id, StudentDTO studentDTO) {
		Student student = studentRepository.findById(id).orElseThrow(NotFoundException::new);
		studentRepository.save(mapToEntity(studentDTO));
	}

	public void delete(Long id) {
		studentRepository.deleteById(id);
	}

	private StudentDTO mapToDTO(Student student) {
		return studentMapper.toDTO(student);
	}

	private Student mapToEntity(StudentDTO studentDTO) {
		return studentMapper.toEntity(studentDTO, courseRepository);
	}

}
