package com.example.coursemanagement.models.dto.response.course_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseInstructorResponse {
    private Long id;
    private String name;
}
