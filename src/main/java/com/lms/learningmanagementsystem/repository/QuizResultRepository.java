import org.springframework.transaction.annotation.Transactional;
package com.lms.learningmanagementsystem.repository;

import com.lms.learningmanagementsystem.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

    List<QuizResult> findByStudentId(Long studentId);
    
    @Transactional
void deleteByStudentId(Long studentId);
    void deleteByStudentId(Long studentId);

    void deleteByIdAndStudentId(Long id, Long studentId);
}
