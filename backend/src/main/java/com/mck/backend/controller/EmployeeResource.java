package com.mck.backend.controller;

import com.mck.backend.model.EmployeeDTO;
import com.mck.backend.service.EmployeeService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/employees", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeResource {

	private final EmployeeService employeeService;

	public EmployeeResource(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/departments/{departmentId}")
	public ResponseEntity<Page<EmployeeDTO>> getEmployeesByDepartmentId(
			@PathVariable(name = "departmentId") Long departmentId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String searchText) {
		Pageable pageable = PageRequest.of(page, size);
		Page<EmployeeDTO> employees = employeeService.findByDepartmentId(departmentId, pageable, searchText);
		return ResponseEntity.ok(employees);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(employeeService.get(id));
	}

	@PostMapping
	@ApiResponse(responseCode = "201")
	public ResponseEntity<Long> createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
		Long createdId = employeeService.create(employeeDTO);
		return new ResponseEntity<>(createdId, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Long> updateEmployee(@PathVariable(name = "id") Long id,
			@RequestBody @Valid EmployeeDTO employeeDTO) {
		employeeService.update(id, employeeDTO);
		return ResponseEntity.ok(id);
	}

	@DeleteMapping("/{id}")
	@ApiResponse(responseCode = "204")
	public ResponseEntity<Void> deleteEmployee(@PathVariable(name = "id") Long id) {
		employeeService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
