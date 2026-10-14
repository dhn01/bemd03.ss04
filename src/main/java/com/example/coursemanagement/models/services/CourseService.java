package com.example.coursemanagement.models.services;

import com.example.coursemanagement.config.mappers.MyMapper;
import com.example.coursemanagement.models.dto.request.course_request.CourseCreateRequest;
import com.example.coursemanagement.models.dto.request.course_request.CourseUpdateRequest;
import com.example.coursemanagement.models.dto.response.course_response.CourseResponse;
import com.example.coursemanagement.models.entities.Course;
import com.example.coursemanagement.models.entities.Instructor;
import com.example.coursemanagement.models.repository.CourseRepository;
import com.example.coursemanagement.models.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final MyMapper courseMapper;

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toResponse)
                .toList();
    }

    public CourseResponse getCourseById(Long id) {
        return courseRepository.findById(id)
                .map(courseMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public CourseResponse createCourse(CourseCreateRequest courseCreateRequest) {
        Instructor existInstructor = instructorRepository.findById(courseCreateRequest.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
        Course course = new Course();

        course.setTitle(courseCreateRequest.getTitle());
        course.setStatus(courseCreateRequest.getStatus());
        course.setInstructor(existInstructor);

        courseRepository.save(course);

        return courseMapper.toResponse(course);
    }

    public CourseResponse updateCourse(Long id, CourseUpdateRequest courseUpdateRequest) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        Instructor existInstructor = instructorRepository.findById(courseUpdateRequest.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        existing.setTitle(courseUpdateRequest.getTitle());
        existing.setStatus(courseUpdateRequest.getStatus());
        existing.setInstructor(existInstructor);

        courseRepository.save(existing);
        return courseMapper.toResponse(existing);
    }

    public void deleteCourseById(Long id) {
        CourseResponse course = getCourseById(id);
        courseRepository.deleteById(id);
    }

}
