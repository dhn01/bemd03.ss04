package com.example.coursemanagement.models.repository;

import com.example.coursemanagement.models.entities.Course;
import com.example.coursemanagement.models.entities.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Instructor findInstructorByCoursesOrId(List<Course> courses, Long id);
}
