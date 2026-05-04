package org.example.miniusos.repository;

import org.example.miniusos.model.CourseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDetailRepository extends JpaRepository<CourseDetail, Long> {
    boolean existsByName(String name);
}
