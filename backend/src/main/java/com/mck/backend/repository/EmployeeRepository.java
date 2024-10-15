package com.mck.backend.repository;

import com.mck.backend.domain.Department;
import com.mck.backend.domain.Employee;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Page<Employee> findByDepartment(Department department, Pageable pageable);

  @Query("""
        SELECT e FROM Employee e
        WHERE e.department = :department
        AND (
          LOWER(e.name) LIKE LOWER(CONCAT('%', :searchText, '%'))
          OR LOWER(e.surname) LIKE LOWER(CONCAT('%', :searchText, '%'))
          OR LOWER(e.email) LIKE LOWER(CONCAT('%', :searchText, '%'))
          OR LOWER(e.phone) LIKE LOWER(CONCAT('%', :searchText, '%'))
        )
      """)
  Page<Employee> findByDepartmentAndSearchText(Department department, String searchText,
      Pageable pageable);

  Optional<Employee> findFirstByDepartment(Department department);
}
