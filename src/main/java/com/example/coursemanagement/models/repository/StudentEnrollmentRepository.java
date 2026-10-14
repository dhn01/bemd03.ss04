package com.example.coursemanagement.models.repository;

import com.example.coursemanagement.models.entities.Course;
import com.example.coursemanagement.models.entities.Student;
import com.example.coursemanagement.models.entities.StudentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Long> {

    List<StudentEnrollment> findAllByCourseId(Long courseId);

    StudentEnrollment findByStudentAndCourse(Student student, Course course);
}
