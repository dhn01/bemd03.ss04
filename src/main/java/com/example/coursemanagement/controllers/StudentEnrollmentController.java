package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.dto.request.enroll_course_request.CourseDropoutRequest;
import com.example.coursemanagement.models.dto.request.enroll_course_request.CourseEnrollmentRequest;
import com.example.coursemanagement.models.dto.response.ApiResponse;
import com.example.coursemanagement.models.dto.response.student_enroll_response.CourseEnrollmentResponse;
import com.example.coursemanagement.models.dto.response.student_response.StudentResponse;
import com.example.coursemanagement.models.entities.StudentEnrollment;
import com.example.coursemanagement.models.services.StudentEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StudentEnrollmentController {
    private final StudentEnrollmentService studentEnrollmentService;

    // get all enrollments
    @GetMapping("/students-enrollments")
    public ResponseEntity<ApiResponse<List<CourseEnrollmentResponse>>> getAllEnrollment() {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        "Fetched enrollments successfully",
                        studentEnrollmentService.getAllStudentEnrollment()
                )
        );
    }

    // get enrollment by id
    @GetMapping("/enrollments/{id}")
    public ResponseEntity<ApiResponse<CourseEnrollmentResponse>> getEnrollmentById(@PathVariable Long id) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse.success(
                            "Fetched enrollment successfully",
                            studentEnrollmentService.getStudentEnrollmentById(id)
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    // update enrollment
    @PutMapping("/students-enrollments/{id}")
    public ResponseEntity<ApiResponse<CourseEnrollmentResponse>> updateStudentEnrollment(@PathVariable Long id, @RequestBody StudentEnrollment studentEnrollment) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse.success(
                            "Updated enrollment successfully",
                            studentEnrollmentService.updateStudentEnrollment(id, studentEnrollment)
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    // delete enrollment
    @DeleteMapping("/students-enrollments/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEnrollment(@PathVariable Long id) {
        try {
            studentEnrollmentService.deleteEnrollmentById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    // get enrollment
    @GetMapping("/courses/{courseId}/enrollments/students")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudentEnrollCourse(@PathVariable Long courseId, @RequestParam(name = "search", defaultValue = "") String search) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse.success(
                            "All student in course with id " + courseId,
                            studentEnrollmentService.getStudentInCourse(courseId, search)
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    // Enroll student vào course
    @PostMapping("/courses/{courseId}/enrollments")
    public ResponseEntity<ApiResponse<CourseEnrollmentResponse>> enrollCourse(@RequestBody CourseEnrollmentRequest courseEnrollmentRequest, @PathVariable Long courseId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse.success(
                            "Enrollment successfully",
                            studentEnrollmentService.enrollStudent(courseEnrollmentRequest.getStudentId(), courseId)
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }

    // delete enrollment of student in course
    @DeleteMapping("/courses/{courseId}/students/{studentId}")
    public ResponseEntity<ApiResponse<String>> deleteStudentInCourse(@PathVariable Long courseId, @RequestBody CourseDropoutRequest courseDropoutRequest) {
        try {
            studentEnrollmentService.deleteStudentInCourse(courseDropoutRequest.getStudentId(), courseId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.error(e.getMessage())
            );
        }

    }

}
