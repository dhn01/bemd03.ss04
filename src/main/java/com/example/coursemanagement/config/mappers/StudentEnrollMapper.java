package com.example.coursemanagement.config.mappers;

import com.example.coursemanagement.models.dto.response.student_enroll_response.CourseEnrollmentResponse;
import com.example.coursemanagement.models.entities.StudentEnrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentEnrollMapper {
    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "course.id", target = "courseId")
    CourseEnrollmentResponse toResponse(StudentEnrollment studentEnrollment);

    List<CourseEnrollmentResponse> toResponses(List<StudentEnrollment> studentEnrollments);
}
