package org.example.miniusos.mappers;

import org.example.miniusos.dto.course.*;
import org.example.miniusos.model.CourseDetail;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CourseDetailMapper {
    ResponseCourseDetailDto toDto(CourseDetail courseDetail);
    CourseDetail toEntity(CreateCourseDetailDto courseDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateCourseDetailDto courseDto, @MappingTarget CourseDetail courseDetail);
}
