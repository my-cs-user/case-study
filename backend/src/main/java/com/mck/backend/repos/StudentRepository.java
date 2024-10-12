package com.mck.backend.repos;

import com.mck.backend.domain.Course;
import com.mck.backend.domain.Student;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, Long> {

  List<Student> findAllByCourses(Course course);

  Page<Student> findAllByCourses(Course course, Pageable pageable);

}
