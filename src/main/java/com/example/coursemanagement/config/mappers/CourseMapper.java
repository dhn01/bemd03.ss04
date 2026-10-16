package com.example.coursemanagement.config.mappers;

import com.example.coursemanagement.models.dto.response.course_response.CourseResponse;
import com.example.coursemanagement.models.entities.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseResponse toResponse(Course course);
}
