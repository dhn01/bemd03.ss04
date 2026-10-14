package com.example.coursemanagement.models.services;

import com.example.coursemanagement.config.mappers.StudentEnrollMapper;
import com.example.coursemanagement.config.mappers.StudentMapper;
import com.example.coursemanagement.models.dto.response.student_enroll_response.CourseEnrollmentResponse;
import com.example.coursemanagement.models.dto.response.student_response.StudentResponse;
import com.example.coursemanagement.models.entities.Course;
import com.example.coursemanagement.models.entities.Student;
import com.example.coursemanagement.models.entities.StudentEnrollment;
import com.example.coursemanagement.models.repository.CourseRepository;
import com.example.coursemanagement.models.repository.StudentEnrollmentRepository;
import com.example.coursemanagement.models.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentEnrollmentService {
    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final StudentEnrollMapper studentEnrollMapper;
    private final StudentMapper studentMapper;

    // get all enrollments
    public List<CourseEnrollmentResponse> getAllStudentEnrollment() {
        return studentEnrollMapper.toResponses(studentEnrollmentRepository.findAll());
    }

    // get enrollment by id
    public CourseEnrollmentResponse getStudentEnrollmentById(Long id) {
        return studentEnrollmentRepository.findById(id)
                .map(studentEnrollMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
    }

    // create enrollment
    public CourseEnrollmentResponse enrollStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!course.getStatus().toString().equals("ACTIVE")) {
            throw new RuntimeException("Cannot enroll not active course");
        }

        StudentEnrollment studentEnrollment = studentEnrollmentRepository.findByStudentAndCourse(student, course);

        if (studentEnrollment == null) {
            StudentEnrollment createStudentEnrollment = StudentEnrollment.builder()
                    .student(student)
                    .course(course)
                    .enrolledAt(LocalDateTime.now())
                    .build();
            studentEnrollmentRepository.save(createStudentEnrollment);
            return studentEnrollMapper.toResponse(createStudentEnrollment);
        } else {
            throw new RuntimeException("Student had already enrolled");
        }
    }

    // update enrollment by id
    public CourseEnrollmentResponse updateStudentEnrollment(Long id, StudentEnrollment studentEnrollment) {

        StudentEnrollment updateStudentEnrollment = studentEnrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        updateStudentEnrollment.setStudent(studentEnrollment.getStudent());
        updateStudentEnrollment.setCourse(studentEnrollment.getCourse());
        studentEnrollmentRepository.save(updateStudentEnrollment);

        return studentEnrollMapper.toResponse(updateStudentEnrollment);
    }

    // delete enrollment by id
    public void deleteEnrollmentById(Long id) {
        getStudentEnrollmentById(id);
        studentEnrollmentRepository.deleteById(id);
    }

    // get all student in course
    public List<StudentResponse> getStudentInCourse(Long courseId, String studentName) {
       List<StudentResponse> studentResponseList =  studentEnrollmentRepository.findAllByCourseId(courseId)
                .stream()
                .map(s-> studentMapper.toResponse(s.getStudent()))
                .toList();

       if (studentName.isBlank()) {
           return studentResponseList;
       }
       return studentResponseList
               .stream()
               .filter(student -> student.getName().contains(studentName))
               .toList();
    }

    // delete student in course
    public void deleteStudentInCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        StudentEnrollment studentEnrollment = studentEnrollmentRepository.findByStudentAndCourse(student, course);

        if (studentEnrollment == null) {
            throw new RuntimeException("Enrollment not found");
        }
        studentEnrollmentRepository.deleteById(studentEnrollment.getId());
    }
}
