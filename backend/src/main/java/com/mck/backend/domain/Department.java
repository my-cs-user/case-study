package com.mck.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Department")
@Getter
@Setter
public class Department extends AbstractEntity {

  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "department")
  private Set<Employee> employees;

}
