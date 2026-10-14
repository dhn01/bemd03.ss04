package com.example.coursemanagement.models.dto.response.instructor_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructorResponse {
    private Long id;
    private String name;
    private String email;
    private List<InstructorCourseResponse> courses;
}
