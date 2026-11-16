package com.lms.learningmanagementsystem;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.lms.learningmanagementsystem.repository.CourseRepository;

@RestController
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course course) {
    return courseRepository.save(course);
}

@GetMapping("/courses")
public List<Course> getCourses() {
    return courseRepository.findAll();
}

@GetMapping("/courses/{id}")
public Course getCourseById(@PathVariable Long id) {
    return courseRepository.findById(id).orElse(null);
}
@PutMapping("/courses/{id}")
public Course updateCourse(@PathVariable Long id, @RequestBody Course course) {
    Course existingCourse = courseRepository.findById(id).orElse(null);

    if (existingCourse != null) {
        existingCourse.setTitle(course.getTitle());
        existingCourse.setDescription(course.getDescription());

        return courseRepository.save(existingCourse);
    }

    return null;
}

@DeleteMapping("/courses/{id}")
public String deleteCourse(@PathVariable Long id) {
    courseRepository.deleteById(id);
    return "Course deleted successfully!";
}

}