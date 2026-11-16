
package com.lms.learningmanagementsystem;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.lms.learningmanagementsystem.repository.StudentRepository;
import com.lms.learningmanagementsystem.repository.CourseRepository;

import com.lms.learningmanagementsystem.repository.EnrollmentRepository;

@RestController
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

   public EnrollmentController(
        EnrollmentRepository enrollmentRepository,
        StudentRepository studentRepository,
        CourseRepository courseRepository) {

    this.enrollmentRepository = enrollmentRepository;
    this.studentRepository = studentRepository;
    this.courseRepository = courseRepository;
}

    @PostMapping("/enrollments")
public ResponseEntity<?> enrollStudent(
        @RequestBody Enrollment enrollment) {

    if (!studentRepository.existsById(enrollment.getStudentId())) {
    return ResponseEntity.badRequest()
            .body("Student does not exist!");
}

if (!courseRepository.existsById(enrollment.getCourseId())) {
    return ResponseEntity.badRequest()
            .body("Course does not exist!");
}
    
    boolean alreadyEnrolled =
            enrollmentRepository.existsByStudentIdAndCourseId(
                    enrollment.getStudentId(),
                    enrollment.getCourseId());

    if (alreadyEnrolled) {
        return ResponseEntity
                .badRequest()
                .body("Student is already enrolled in this course!");
    }

    Enrollment savedEnrollment =
            enrollmentRepository.save(enrollment);

    return ResponseEntity.ok(savedEnrollment);
}
   

    @GetMapping("/enrollments")
    public List<Enrollment> getEnrollments() {
        return enrollmentRepository.findAll();
    }

    @GetMapping("/enrollments/student/{studentId}")
public List<Enrollment> getStudentEnrollments(
        @PathVariable Long studentId) {

    return enrollmentRepository.findAll().stream()
            .filter(enrollment ->
                    enrollment.getStudentId().equals(studentId))
            .toList();
}
}