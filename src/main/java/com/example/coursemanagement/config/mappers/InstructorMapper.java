package com.example.coursemanagement.config.mappers;

import com.example.coursemanagement.models.dto.response.instructor_response.InstructorResponse;
import com.example.coursemanagement.models.entities.Instructor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InstructorMapper {
    InstructorResponse toResponse(Instructor instructor);

    List<InstructorResponse> toResponses(List<Instructor> instructors);
}
