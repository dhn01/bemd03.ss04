package com.example.coursemanagement.models.dto.request.instructor_request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstructorCreateRequest {
    private String name;
    private String email;
}
