package com.example.coursemanagement.models.dto.response.course_response;

import com.example.coursemanagement.enums.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResponseV2 {
    private Long id;
    private String title;
    private CourseStatus status;
}
