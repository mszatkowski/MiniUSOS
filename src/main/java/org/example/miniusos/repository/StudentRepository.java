package org.example.miniusos.repository;

import org.example.miniusos.model.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByIndexNumber(String indexNumber);

    @EntityGraph(attributePaths = {"enrollments", "enrollments.course"})
    List<Student> findByEnrollmentsCourseName(String courseName);
}
