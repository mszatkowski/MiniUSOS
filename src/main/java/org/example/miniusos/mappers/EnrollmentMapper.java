package org.example.miniusos.mappers;

import org.example.miniusos.dto.enrollment.ResponseEnrollmentDto;
import org.example.miniusos.model.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.name", target = "courseName")
    @Mapping(source = "student.id", target = "studentId")
    ResponseEnrollmentDto toDto(Enrollment enrollment);
}
