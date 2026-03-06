package org.example.miniusos.mappers;

import org.example.miniusos.dto.course.*;
import org.example.miniusos.model.Course;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    ResponseCourseDto toDto(Course course);
    Course toEntity(CreateCourseDto courseDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateCourseDto courseDto, @MappingTarget Course course);
}
