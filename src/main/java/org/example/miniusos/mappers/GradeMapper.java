package org.example.miniusos.mappers;

import org.example.miniusos.dto.grade.*;
import org.example.miniusos.model.Grade;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface GradeMapper {
    ResponseGradeDto toDto(Grade grade);
    Grade toEntity(CreateGradeDto gradeDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateGradeDto gradeDto, @MappingTarget Grade grade);
}
