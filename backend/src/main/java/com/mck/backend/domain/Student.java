package com.mck.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Student")
@Getter
@Setter
public class Student extends AbstractEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String surname;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String phone;

  @ManyToMany
  @JoinTable(
      name = "StudentCourse",
      joinColumns = @JoinColumn(name = "studentId"),
      inverseJoinColumns = @JoinColumn(name = "courseId")
  )
  private Set<Course> courses;

}
