package com.mck.backend.rest;

import com.mck.backend.model.CourseDTO;
import com.mck.backend.service.CourseService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
public class CourseResource {

  private final CourseService courseService;

  public CourseResource(final CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<CourseDTO> getCourse(@PathVariable(name = "id") Long id) {
    return ResponseEntity.ok(courseService.get(id));
  }

  @PostMapping
  @ApiResponse(responseCode = "201")
  public ResponseEntity<Long> createCourse(@RequestBody @Valid CourseDTO courseDTO) {
    final Long createdId = courseService.create(courseDTO);
    return new ResponseEntity<>(createdId, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Long> updateCourse(@PathVariable(name = "id") Long id,
      @RequestBody @Valid final CourseDTO courseDTO) {
    courseService.update(id, courseDTO);
    return ResponseEntity.ok(id);
  }

  @DeleteMapping("/{id}")
  @ApiResponse(responseCode = "204")
  public ResponseEntity<Void> deleteCourse(@PathVariable(name = "id") Long id) {
    courseService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
