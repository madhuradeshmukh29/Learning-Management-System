
package com.lms.learningmanagementsystem;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lms.learningmanagementsystem.repository.LessonRepository;

@RestController
public class LessonController {

    private final LessonRepository lessonRepository;

    public LessonController(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @PostMapping("/lessons")
    public Lesson addLesson(@RequestBody Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @GetMapping("/lessons")
    public List<Lesson> getLessons() {
        return lessonRepository.findAll();
    }
    @DeleteMapping("/lessons/{id}")
 public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
    if (!lessonRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
    }

    lessonRepository.deleteById(id);
    return ResponseEntity.noContent().build();
}


@org.springframework.web.bind.annotation.PutMapping("/lessons/{id}")
public ResponseEntity<Lesson> updateLesson(
        @PathVariable Long id,
        @RequestBody Lesson updatedLesson) {

    return lessonRepository.findById(id)
            .map(lesson -> {
                lesson.setCourseId(updatedLesson.getCourseId());
                lesson.setTitle(updatedLesson.getTitle());
                lesson.setContent(updatedLesson.getContent());

                Lesson savedLesson = lessonRepository.save(lesson);
                return ResponseEntity.ok(savedLesson);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
}

}