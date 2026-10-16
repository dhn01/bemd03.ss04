package com.example.coursemanagement.models.repository;

import com.example.coursemanagement.models.dto.response.student_response.StudentResponse;
import com.example.coursemanagement.models.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("""
            SELECT new com.example.coursemanagement.models.dto.response.student_response.StudentResponse(
                s.name,
                s.email
            )
            FROM Student s
            WHERE :search IS NULL OR s.name ILIKE CONCAT('%', :search, '%') 
            """)
    Page<StudentResponse> findAllStudentsBy(@Param(value = "search") String search , Pageable pageable);
}
