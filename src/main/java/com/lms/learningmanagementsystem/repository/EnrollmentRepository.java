
package com.lms.learningmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lms.learningmanagementsystem.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}