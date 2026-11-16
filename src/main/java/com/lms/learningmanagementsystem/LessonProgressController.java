
package com.lms.learningmanagementsystem;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.lms.learningmanagementsystem.repository.LessonProgressRepository;

@RestController
public class LessonProgressController {

    private final LessonProgressRepository progressRepository;

    public LessonProgressController(
            LessonProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @PostMapping("/progress")
    public LessonProgress markCompleted(
            @RequestBody LessonProgress progress) {

        progress.setCompleted(true);

        return progressRepository
                .findByStudentIdAndLessonId(
                        progress.getStudentId(),
                        progress.getLessonId())
                .map(existing -> {
                    existing.setCompleted(true);
                    return progressRepository.save(existing);
                })
                .orElseGet(() -> progressRepository.save(progress));
    }

    @GetMapping("/progress/{studentId}")
    public java.util.List<LessonProgress> getProgress(
            @PathVariable Long studentId) {

        return progressRepository.findAll()
                .stream()
                .filter(progress ->
                        progress.getStudentId().equals(studentId))
                .toList();
    }
}
