package com.lms.learningmanagementsystem;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

import com.lms.learningmanagementsystem.repository.StudentRepository;

@RestController
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }
    @GetMapping("/students")
    public List<Student> getStudents() {
    return studentRepository.findAll();
    }

@GetMapping("/students/{id}")
public Student getStudentById(@PathVariable Long id) {
    return studentRepository.findById(id).orElse(null);
}

@PutMapping("/students/{id}")
public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
    Student existingStudent = studentRepository.findById(id).orElse(null);

    if (existingStudent != null) {
        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setPassword(student.getPassword());

        return studentRepository.save(existingStudent);
    }

    return null;
}

@DeleteMapping("/students/{id}")
public String deleteStudent(@PathVariable Long id) {
    studentRepository.deleteById(id);
    return "Student deleted successfully!";
}
}