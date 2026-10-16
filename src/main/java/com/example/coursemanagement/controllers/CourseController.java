package com.example.coursemanagement.controllers;

import com.example.coursemanagement.enums.CourseStatus;
import com.example.coursemanagement.models.dto.request.course_request.CourseCreateRequest;
import com.example.coursemanagement.models.dto.request.course_request.CourseUpdateRequest;
import com.example.coursemanagement.models.dto.response.ApiResponse;
import com.example.coursemanagement.models.dto.response.PageResponse;
import com.example.coursemanagement.models.dto.response.course_response.CourseResponse;
import com.example.coursemanagement.models.dto.response.course_response.CourseResponseV2;
import com.example.coursemanagement.models.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<ApiResponse<PageResponse<CourseResponseV2>>> getAllCourses(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", defaultValue = "") String sortBy,
            @RequestParam(name = "direction", defaultValue = "") Sort.Direction direction,
            @RequestParam(name = "status", defaultValue = "") CourseStatus status,
            @RequestParam(name = "search", defaultValue = "") String search
    ) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Fetched courses successfully",
                        courseService.getPagedCoursesByStatusV2(page, size, sortBy, direction, status, search)
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
