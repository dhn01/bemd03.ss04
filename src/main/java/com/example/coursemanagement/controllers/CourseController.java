package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.dto.request.course_request.CourseCreateRequest;
import com.example.coursemanagement.models.dto.request.course_request.CourseUpdateRequest;
import com.example.coursemanagement.models.dto.response.ApiResponse;
import com.example.coursemanagement.models.dto.response.course_response.CourseResponse;
import com.example.coursemanagement.models.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;

    // get all courses
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAllCourses() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Fetched courses successfully",
                        courseService.getAllCourses()
                )
        );
    }

    // get course by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getCourse(@PathVariable Long id) {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse.success(
                            "Found course with id: " + id,
                            courseService.getCourseById(id)
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    // create new course
    @PostMapping("")
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(@RequestBody CourseCreateRequest courseCreateRequest) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    ApiResponse.success(
                            "Created successfully",
                            courseService.createCourse(courseCreateRequest)
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiResponse.error(
                            e.getMessage()
                    )
            );
        }
    }

    // update course by id
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(@PathVariable Long id, @RequestBody CourseUpdateRequest courseUpdateRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse.success(
                            "Updated successfully",
                            courseService.updateCourse(id, courseUpdateRequest)
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiResponse.error(
                            e.getMessage()
                    )
            );
        }
    }

    // delete course by id
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCourse(@PathVariable Long id) {
        try {
            courseService.deleteCourseById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }
}
