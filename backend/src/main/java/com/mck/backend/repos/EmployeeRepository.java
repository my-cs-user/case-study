package com.mck.backend.repos;

import com.mck.backend.domain.Department;
import com.mck.backend.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Employee findFirstByDepartment(Department department);

  Page<Employee> findByDepartment(Department department, Pageable pageable);

}
