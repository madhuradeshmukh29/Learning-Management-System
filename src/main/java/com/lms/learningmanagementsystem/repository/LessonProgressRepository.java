
package com.lms.learningmanagementsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.learningmanagementsystem.LessonProgress;

public interface LessonProgressRepository
        extends JpaRepository<LessonProgress, Long> {

    Optional<LessonProgress> findByStudentIdAndLessonId(
            Long studentId, Long lessonId);
}
