package com.example.coursemanagement.controllers;

import com.example.coursemanagement.models.dto.response.ApiResponse;
import com.example.coursemanagement.models.dto.response.student_response.StudentResponse;
import com.example.coursemanagement.models.entities.Student;
import com.example.coursemanagement.models.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentControllers {
    private final StudentService studentService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Fetched student successfully",
                        studentService.getAllStudents()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(
                    ApiResponse.success(
                            "Fetched student with id: " + id,
                            studentService.getStudentById(id)
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            ApiResponse.error(e.getMessage())
                    );
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<StudentResponse>> createStudent(@RequestBody Student student) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(
                            "Created student successfully",
                            studentService.addStudent(student)
                    ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(
                            ApiResponse.error(e.getMessage())
                    );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudent(@RequestBody Student student, @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success(
                            "Updated student successfully",
                            studentService.updateStudent(id, student)
                    ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(
                            ApiResponse.error(e.getMessage())
                    );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStudentById(@PathVariable Long id) {
        try {
            studentService.deleteStudentById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
