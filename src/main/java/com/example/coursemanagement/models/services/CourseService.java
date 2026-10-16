package com.example.coursemanagement.models.services;

import com.example.coursemanagement.config.mappers.CourseMapper;
import com.example.coursemanagement.config.mappers.PageResMapper;
import com.example.coursemanagement.enums.CourseStatus;
import com.example.coursemanagement.models.dto.request.course_request.CourseCreateRequest;
import com.example.coursemanagement.models.dto.request.course_request.CourseUpdateRequest;
import com.example.coursemanagement.models.dto.response.PageResponse;
import com.example.coursemanagement.models.dto.response.course_response.CourseResponse;
import com.example.coursemanagement.models.dto.response.course_response.CourseResponseV2;
import com.example.coursemanagement.models.entities.Course;
import com.example.coursemanagement.models.entities.Instructor;
import com.example.coursemanagement.models.repository.CourseRepository;
import com.example.coursemanagement.models.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final CourseMapper courseMapper;
    private final PageResMapper pageResMapper;

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

    public PageResponse<CourseResponse> getPagedCourses(
            int page,
            int size,
            String sortBy,
            Sort.Direction direction
    ) {
        if (page < 0) {
            page = 0;
        }

        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "id";
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return pageResMapper.toResponse(
                courseRepository.findAll(pageable),
                courseMapper::toResponse
        );
    }

    public PageResponse<CourseResponse> getPagedCoursesByStatus(
            int page,
            int size,
            String sortBy,
            Sort.Direction direction,
            CourseStatus status) {

        if (page < 0) {
            page = 0;
        }

        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "id";
        }

        if (direction == null) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return pageResMapper.toResponse(
                courseRepository.findAllByStatus(status, pageable),
                courseMapper::toResponse
        );

    }

    public PageResponse<CourseResponseV2> getPagedCoursesByStatusV2(
            int page,
            int size,
            String sortBy,
            Sort.Direction direction,
            CourseStatus status,
            String search
    ) {

        if (page < 0) {
            page = 0;
        }

        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "id";
        }

        if (direction == null) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return pageResMapper.toResponse(
                courseRepository.findAllByStatusV2(status, search, pageable)
        );
    }

}
