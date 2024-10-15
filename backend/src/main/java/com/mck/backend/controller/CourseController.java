package com.mck.backend.controller;

import com.mck.backend.mapper.CourseMapper;
import com.mck.backend.model.CourseDTO;
import com.mck.backend.request.CreateCourseRequest;
import com.mck.backend.request.UpdateCourseRequest;
import com.mck.backend.service.CourseService;
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
@RequestMapping(value = "/api/courses", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseController {

  private final CourseService courseService;

  private final CourseMapper courseMapper;

  public CourseController(CourseService courseService, CourseMapper courseMapper) {
    this.courseService = courseService;
    this.courseMapper = courseMapper;
  }

  @GetMapping
  public ResponseEntity<List<CourseDTO>> getAllCourses() {
    return ResponseEntity.ok(courseService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CourseDTO> getCourse(@PathVariable(name = "id") Long id) {
    return ResponseEntity.ok(courseService.get(id));
  }

  @PostMapping
  @ApiResponse(responseCode = "201")
  public ResponseEntity<Long> createCourse(@RequestBody @Valid CreateCourseRequest request) {
    Long createdId = courseService.create(courseMapper.toDTO(request));
    return new ResponseEntity<>(createdId, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Long> updateCourse(@PathVariable(name = "id") Long id,
      @RequestBody @Valid UpdateCourseRequest request) {
    courseService.update(courseMapper.toDTO(id, request));
    return ResponseEntity.ok(id);
  }

  @DeleteMapping("/{id}")
  @ApiResponse(responseCode = "204")
  public ResponseEntity<Void> deleteCourse(@PathVariable(name = "id") Long id) {
    courseService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
