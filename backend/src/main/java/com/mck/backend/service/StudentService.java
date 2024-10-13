package com.mck.backend.service;

import static org.apache.commons.lang3.StringUtils.isBlank;

import com.mck.backend.domain.Course;
import com.mck.backend.domain.Student;
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

	public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
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
		return students.map(student -> mapToDTO(student, new StudentDTO()));
	}

	public StudentDTO get(Long id) {
		return studentRepository.findById(id)
			.map(student -> mapToDTO(student, new StudentDTO()))
			.orElseThrow(NotFoundException::new);
	}

	public Long create(StudentDTO studentDTO) {
		Student student = new Student();
		mapToEntity(studentDTO, student);
		return studentRepository.save(student).getId();
	}

	public void update(Long id, StudentDTO studentDTO) {
		Student student = studentRepository.findById(id).orElseThrow(NotFoundException::new);
		mapToEntity(studentDTO, student);
		studentRepository.save(student);
	}

	public void delete(Long id) {
		studentRepository.deleteById(id);
	}

	private StudentDTO mapToDTO(Student student, StudentDTO studentDTO) {
		studentDTO.setId(student.getId());
		studentDTO.setName(student.getName());
		studentDTO.setSurname(student.getSurname());
		studentDTO.setEmail(student.getEmail());
		studentDTO.setPhone(student.getPhone());
		studentDTO.setCourses(student.getCourses().stream().map(course -> course.getId()).toList());
		return studentDTO;
	}

	private Student mapToEntity(StudentDTO studentDTO, Student student) {
		student.setName(studentDTO.getName());
		student.setSurname(studentDTO.getSurname());
		student.setEmail(studentDTO.getEmail());
		student.setPhone(studentDTO.getPhone());
		List<Course> courses = courseRepository
			.findAllById(studentDTO.getCourses() == null ? Collections.emptyList() : studentDTO.getCourses());
		if (courses.size() != (studentDTO.getCourses() == null ? 0 : studentDTO.getCourses().size())) {
			throw new NotFoundException("one of courses not found");
		}
		student.setCourses(new HashSet<>(courses));
		return student;
	}

}
