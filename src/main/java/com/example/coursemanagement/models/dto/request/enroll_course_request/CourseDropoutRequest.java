package com.example.coursemanagement.models.dto.request.enroll_course_request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDropoutRequest {
    private Long studentId;
}
