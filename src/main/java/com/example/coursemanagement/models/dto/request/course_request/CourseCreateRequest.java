package com.example.coursemanagement.models.dto.request.course_request;

import com.example.coursemanagement.enums.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseCreateRequest {
    private String title;
    private CourseStatus status;
    private Long instructorId;
}
