
package com.lms.learningmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lms.learningmanagementsystem.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}