
package com.lms.learningmanagementsystem.repository;

import com.lms.learningmanagementsystem.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    List<QuizQuestion> findByCourseId(Long courseId);
}
