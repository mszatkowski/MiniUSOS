package org.example.miniusos.mappers;

import org.example.miniusos.dto.student.*;
import org.example.miniusos.model.Student;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {EnrollmentMapper.class})
public interface StudentMapper {
    ResponseStudentDto toDto(Student student);
    Student toEntity(CreateStudentDto studentDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateStudentDto studentDto, @MappingTarget Student student);
}
