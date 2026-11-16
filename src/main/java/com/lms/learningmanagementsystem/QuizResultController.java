
package com.lms.learningmanagementsystem;

import com.lms.learningmanagementsystem.repository.QuizResultRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/quiz-results")
@CrossOrigin
public class QuizResultController {

    private final QuizResultRepository quizResultRepository;

    public QuizResultController(QuizResultRepository quizResultRepository) {
        this.quizResultRepository = quizResultRepository;
    }

    @GetMapping("/student/{studentId}")
    public List<QuizResult> getStudentResults(@PathVariable Long studentId) {
        return quizResultRepository.findByStudentId(studentId);
    }

    // Delete one quiz attempt
    @DeleteMapping("/{id}/student/{studentId}")
    public String deleteQuizResult(
            @PathVariable Long id,
            @PathVariable Long studentId) {

        quizResultRepository.deleteByIdAndStudentId(id, studentId);

        return "Quiz result deleted successfully";
    }

    // Delete all quiz history of a student
    @DeleteMapping("/student/{studentId}")
    public String deleteAllQuizHistory(@PathVariable Long studentId) {

        quizResultRepository.deleteByStudentId(studentId);

        return "All quiz history deleted successfully";
    }
}
