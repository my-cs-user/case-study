package com.mck.backend.controller;

import com.mck.backend.model.DepartmentDTO;
import com.mck.backend.service.DepartmentService;
import com.mck.backend.util.ReferencedException;
import com.mck.backend.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/departments", produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartmentController {

	private final DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@GetMapping
	public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
		return ResponseEntity.ok(departmentService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(departmentService.get(id));
	}

	@PostMapping
	@ApiResponse(responseCode = "201")
	public ResponseEntity<Long> createDepartment(@RequestBody @Valid DepartmentDTO departmentDTO) {
		Long createdId = departmentService.create(departmentDTO);
		return new ResponseEntity<>(createdId, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Long> updateDepartment(@PathVariable(name = "id") Long id,
			@RequestBody @Valid DepartmentDTO departmentDTO) {
		departmentService.update(id, departmentDTO);
		return ResponseEntity.ok(id);
	}

	@DeleteMapping("/{id}")
	@ApiResponse(responseCode = "204")
	public ResponseEntity<Void> deleteDepartment(@PathVariable(name = "id") Long id) {
		ReferencedWarning referencedWarning = departmentService.getReferencedWarning(id);
		if (referencedWarning != null) {
			throw new ReferencedException(referencedWarning);
		}
		departmentService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
