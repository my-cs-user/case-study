package com.mck.backend.controller;

import com.mck.backend.mapper.StudentMapper;
import com.mck.backend.model.StudentDTO;
import com.mck.backend.request.CreateStudentRequest;
import com.mck.backend.request.UpdateStudentRequest;
import com.mck.backend.service.StudentService;
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
@RequestMapping(value = "/api/students", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentController {

  private final StudentService studentService;

  private final StudentMapper studentMapper;

  public StudentController(StudentService studentService, StudentMapper studentMapper) {
    this.studentService = studentService;
    this.studentMapper = studentMapper;
  }

  @GetMapping("/{id}")
  public ResponseEntity<StudentDTO> getStudent(@PathVariable(name = "id") Long id) {
    return ResponseEntity.ok(studentService.get(id));
  }

  @GetMapping("/courses/{courseId}")
  public ResponseEntity<Page<StudentDTO>> getAllStudentsByCourseId(
      @PathVariable(name = "courseId") Long courseId,
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
      @RequestParam(required = false) String searchText) {
    Pageable pageable = PageRequest.of(page, size);
    Page<StudentDTO> students = studentService.findByCourseId(courseId, pageable, searchText);
    return ResponseEntity.ok(students);
  }

  @PostMapping
  @ApiResponse(responseCode = "201")
  public ResponseEntity<Long> createStudent(@RequestBody @Valid CreateStudentRequest request) {
    Long createdId = studentService.create(studentMapper.toDTO(request));
    return new ResponseEntity<>(createdId, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Long> updateStudent(@PathVariable(name = "id") Long id,
      @RequestBody @Valid UpdateStudentRequest request) {
    studentService.update(studentMapper.toDTO(id, request));
    return ResponseEntity.ok(id);
  }

  @DeleteMapping("/{id}")
  @ApiResponse(responseCode = "204")
  public ResponseEntity<Void> deleteStudent(@PathVariable(name = "id") Long id) {
    studentService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
