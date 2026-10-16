package com.example.coursemanagement.models.repository;

import com.example.coursemanagement.enums.CourseStatus;
import com.example.coursemanagement.models.dto.response.course_response.CourseResponseV2;
import com.example.coursemanagement.models.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("""
            SELECT c FROM Course c WHERE c.status = :status
            """)
    Page<Course> findAllByStatus(@Param("status") CourseStatus status, Pageable pageable);

    @Query("""
            SELECT new com.example.coursemanagement.models.dto.response.course_response.CourseResponseV2(
                c.id,
                c.title,
                c.status
            )
            FROM Course c 
            WHERE (:status IS NULL OR c.status = :status)
                AND (:search IS NULL OR c.title ILIKE CONCAT('%', :search, '%'))   
            """)
    Page<CourseResponseV2> findAllByStatusV2(@Param("status") CourseStatus status, @Param("search") String search, Pageable pageable);
}
