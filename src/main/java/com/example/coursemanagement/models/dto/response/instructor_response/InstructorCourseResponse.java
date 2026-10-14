package com.example.coursemanagement.models.dto.response.instructor_response;

import com.example.coursemanagement.enums.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstructorCourseResponse {
    private Long id;
    private String title;
    private CourseStatus status;
}
