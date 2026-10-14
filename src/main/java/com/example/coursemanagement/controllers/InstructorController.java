package com.example.coursemanagement.controllers;


import com.example.coursemanagement.models.dto.request.instructor_request.InstructorCreateRequest;
import com.example.coursemanagement.models.dto.response.ApiResponse;
import com.example.coursemanagement.models.dto.response.instructor_response.InstructorResponse;
import com.example.coursemanagement.models.services.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/instructors")
public class InstructorController {
    private final InstructorService instructorService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<InstructorResponse>>> getAllInstructor() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Fetch instructors successfully",
                        instructorService.getAllInstructors()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InstructorResponse>> getInstructorById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            ApiResponse.success(
                                    "Fetch instructor successfully",
                                    instructorService.getInstructorById(id)
                            )
                    );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            ApiResponse.error(
                                    e.getMessage()
                            )
                    );
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<InstructorResponse>> createInstructor(@RequestBody InstructorCreateRequest instructorCreateRequest) {

        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            ApiResponse.success(
                                    "Created instructor successfully",
                                    instructorService.createInstructor(instructorCreateRequest)
                            )
                    );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(
                            ApiResponse.error(e.getMessage())
                    );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InstructorResponse>> updateInstructor(@PathVariable Long id, @RequestBody InstructorCreateRequest instructorCreateRequest) {

        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            ApiResponse.success(
                                    "Updated instructor successfully",
                                    instructorService.updateInstructor(id, instructorCreateRequest)
                            )
                    );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            ApiResponse.error(e.getMessage())
                    );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteInstructor(@PathVariable Long id) {

        try {
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ApiResponse.error(e.getMessage())
            );
        }
    }
}
