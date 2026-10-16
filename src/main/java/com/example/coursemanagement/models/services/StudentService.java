package com.example.coursemanagement.models.services;

import com.example.coursemanagement.config.mappers.PageResMapper;
import com.example.coursemanagement.config.mappers.StudentMapper;
import com.example.coursemanagement.models.dto.response.PageResponse;
import com.example.coursemanagement.models.dto.response.student_response.StudentResponse;
import com.example.coursemanagement.models.entities.Student;
import com.example.coursemanagement.models.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final PageResMapper pageResMapper;

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toResponse)
                .toList();
    }

    public StudentResponse getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(studentMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Student with id: " + id + " not found!"));
    }

    public StudentResponse addStudent(Student student) {
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toResponse(savedStudent);
    }

    public StudentResponse updateStudent(Long id, Student student) {
        Student updatedStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student with id: " + id + " not found!"));

        updatedStudent.setName(student.getName());
        updatedStudent.setEmail(student.getEmail());

        studentRepository.save(updatedStudent);
        return studentMapper.toResponse(updatedStudent);
    }

    public void deleteStudentById(Long id) {
        StudentResponse student = getStudentById(id);
        studentRepository.deleteById(id);
    }

    public PageResponse<StudentResponse> getAllStudentsByPage(int page, int size, String sortBy, Sort.Direction direction, String search) {
        if (page < 0) {
            page = 0;
        }

        if (size < 0) {
            size = 10;
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
                studentRepository.findAllStudentsBy(search, pageable)
                );
    }
}
