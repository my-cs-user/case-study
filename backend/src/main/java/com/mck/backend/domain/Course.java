package com.mck.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Course")
@Getter
@Setter
public class Course extends AbstractEntity {

	@Column(nullable = false)
	private String name;

	@ManyToMany(mappedBy = "courses")
	private Set<Student> students;

}
