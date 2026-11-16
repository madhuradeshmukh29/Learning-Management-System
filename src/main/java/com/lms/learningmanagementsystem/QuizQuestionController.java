
package com.lms.learningmanagementsystem;

import com.lms.learningmanagementsystem.repository.QuizQuestionRepository;
import org.springframework.web.bind.annotation.*;
import com.lms.learningmanagementsystem.repository.QuizResultRepository;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quiz-questions")
public class QuizQuestionController {

    private final QuizQuestionRepository quizQuestionRepository;

    private final QuizResultRepository quizResultRepository;

    public QuizQuestionController(
        QuizQuestionRepository quizQuestionRepository,
        QuizResultRepository quizResultRepository) {
    this.quizQuestionRepository = quizQuestionRepository;
    this.quizResultRepository = quizResultRepository;
}

    @PostMapping
    public QuizQuestion addQuestion(@RequestBody QuizQuestion question) {
        return quizQuestionRepository.save(question);
    }

    @GetMapping
    public List<QuizQuestion> getQuestions(@RequestParam Long courseId) {
        return quizQuestionRepository.findByCourseId(courseId);
    }
    
    @DeleteMapping("/{id}")
public void deleteQuestion(@PathVariable Long id) {
    quizQuestionRepository.deleteById(id);
}
    
@PostMapping("/submit")
public Map<String, Object> submitQuiz(@RequestBody QuizSubmission submission) {

    List<QuizQuestion> questions =
            quizQuestionRepository.findByCourseId(submission.getCourseId());

    int score = 0;
    Map<Long, String> answers = submission.getAnswers();

    if (answers != null) {
        for (QuizQuestion question : questions) {
            String selectedAnswer = answers.get(question.getId());

            if (question.getCorrectAnswer() != null
                    && question.getCorrectAnswer().equals(selectedAnswer)) {
                score++;
            }
        }
    }

    QuizResult result = new QuizResult();
    result.setStudentId(submission.getStudentId());
    result.setCourseId(submission.getCourseId());
    result.setScore(score);
    result.setTotalQuestions(questions.size());

    quizResultRepository.save(result);

    return Map.of(
            "score", score,
            "totalQuestions", questions.size(),
            "message", "Quiz result saved successfully"
    );

 
}
}
