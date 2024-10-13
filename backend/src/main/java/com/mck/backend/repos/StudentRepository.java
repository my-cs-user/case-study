package com.mck.backend.repos;

import com.mck.backend.domain.Course;
import com.mck.backend.domain.Student;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface StudentRepository extends JpaRepository<Student, Long> {

  List<Student> findAllByCourses(Course course);

  Page<Student> findAllByCourses(Course course, Pageable pageable);

  @Query("""
        SELECT s FROM Student s
        JOIN s.courses c
        WHERE c = :course
        AND (
          LOWER(s.name) LIKE LOWER(CONCAT('%', :searchText, '%'))
          OR LOWER(s.surname) LIKE LOWER(CONCAT('%', :searchText, '%'))
          OR LOWER(s.email) LIKE LOWER(CONCAT('%', :searchText, '%'))
          OR LOWER(s.phone) LIKE LOWER(CONCAT('%', :searchText, '%'))
        )
      """)
  Page<Student> findAllByCoursesAndSearchText(Course course, Pageable pageable, String searchText);
}
