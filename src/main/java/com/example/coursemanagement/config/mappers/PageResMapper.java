package com.example.coursemanagement.config.mappers;

import com.example.coursemanagement.models.dto.response.PageResponse;
import com.example.coursemanagement.models.dto.response.course_response.CourseResponse;
import com.example.coursemanagement.models.entities.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface PageResMapper {

    default <T, R> PageResponse<R> toResponse(
            Page<T> page,
            Function<T, R> mapper
    ) {
        Page<R> mappedPage = page.map(mapper);

        return toResponse(mappedPage);
    }

    default <T> PageResponse<T> toResponse(Page<T> page) {
        PageResponse<T> response = new PageResponse<>();
        response.setItems(page.getContent());
        response.setPage(page.getNumber());
        response.setSize(page.getSize());
        response.setTotalItems((int) page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());

        return response;
    }
}
