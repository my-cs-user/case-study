package com.mck.backend.service;

import com.mck.backend.domain.Course;
import com.mck.backend.model.CourseDTO;
import com.mck.backend.repos.CourseRepository;
import com.mck.backend.repos.StudentRepository;
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

  public CourseService(final CourseRepository courseRepository,
      final StudentRepository studentRepository) {
    this.courseRepository = courseRepository;
    this.studentRepository = studentRepository;
  }

  public List<CourseDTO> findAll() {
    final List<Course> courses = courseRepository.findAll(Sort.by("id"));
    return courses.stream()
        .map(course -> mapToDTO(course, new CourseDTO()))
        .toList();
  }

  public CourseDTO get(final Long id) {
    return courseRepository.findById(id)
        .map(course -> mapToDTO(course, new CourseDTO()))
        .orElseThrow(NotFoundException::new);
  }

  public Long create(final CourseDTO courseDTO) {
    final Course course = new Course();
    mapToEntity(courseDTO, course);
    return courseRepository.save(course).getId();
  }

  public void update(final Long id, final CourseDTO courseDTO) {
    final Course course = courseRepository.findById(id)
        .orElseThrow(NotFoundException::new);
    mapToEntity(courseDTO, course);
    courseRepository.save(course);
  }

  public void delete(final Long id) {
    final Course course = courseRepository.findById(id)
        .orElseThrow(NotFoundException::new);
    // remove many-to-many relations at owning side
    studentRepository.findAllByCourses(course)
        .forEach(student -> student.getCourses().remove(course));
    courseRepository.delete(course);
  }

  private CourseDTO mapToDTO(final Course course, final CourseDTO courseDTO) {
    courseDTO.setId(course.getId());
    courseDTO.setName(course.getName());
    return courseDTO;
  }

  private Course mapToEntity(final CourseDTO courseDTO, final Course course) {
    course.setName(courseDTO.getName());
    return course;
  }

}
