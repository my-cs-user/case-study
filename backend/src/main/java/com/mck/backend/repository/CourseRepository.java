package com.mck.backend.repository;

import com.mck.backend.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
