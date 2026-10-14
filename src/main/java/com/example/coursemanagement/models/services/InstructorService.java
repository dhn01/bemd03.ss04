package com.example.coursemanagement.models.services;


import com.example.coursemanagement.config.mappers.InstructorMapper;
import com.example.coursemanagement.models.dto.request.instructor_request.InstructorCreateRequest;
import com.example.coursemanagement.models.dto.response.instructor_response.InstructorResponse;
import com.example.coursemanagement.models.entities.Instructor;
import com.example.coursemanagement.models.repository.CourseRepository;
import com.example.coursemanagement.models.repository.InstructorRepository;
import com.example.coursemanagement.models.repository.StudentEnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final InstructorMapper instructorMapper;

    public List<InstructorResponse> getAllInstructors() {
        return instructorMapper.toResponses(instructorRepository.findAll());
    }

    public InstructorResponse getInstructorById(Long id) {
        return instructorRepository.findById(id)
                .map(instructorMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
    }

    public InstructorResponse createInstructor(InstructorCreateRequest instructorCreateRequest) {
        Instructor instructor = new Instructor();

        instructor.setName(instructorCreateRequest.getName());
        instructor.setEmail(instructorCreateRequest.getEmail());
        instructorRepository.save(instructor);

        return instructorMapper.toResponse(instructor);
    }

    public InstructorResponse updateInstructor(Long id, InstructorCreateRequest instructorCreateRequest) {
        Instructor updatedInstructor = instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        updatedInstructor.setName(instructorCreateRequest.getName());
        updatedInstructor.setEmail(instructorCreateRequest.getEmail());
        instructorRepository.save(updatedInstructor);

        return instructorMapper.toResponse(updatedInstructor);
    }

    public void deleteInstructorById(Long id) {
        InstructorResponse instructor = getInstructorById(id);
        instructorRepository.deleteById(id);
    }
}
