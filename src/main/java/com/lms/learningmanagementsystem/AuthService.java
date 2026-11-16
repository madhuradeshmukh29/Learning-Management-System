
package com.lms.learningmanagementsystem;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lms.learningmanagementsystem.repository.StudentRepository;

@Service
public class AuthService {

    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public AuthService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student register(RegisterRequest request) {

        if (studentRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered!");
        }

        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());

        String encodedPassword =
                passwordEncoder.encode(request.getPassword());

        student.setPassword(encodedPassword);

        return studentRepository.save(student);
    }

  public Student login(String email, String password) {

    Student student = studentRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Email not found!"));

    boolean passwordMatches =
            passwordEncoder.matches(password, student.getPassword());

    if (passwordMatches) {
        return student;
    } else {
        throw new RuntimeException("Incorrect password!");
    }
}  

}