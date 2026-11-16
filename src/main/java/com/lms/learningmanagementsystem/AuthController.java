
package com.lms.learningmanagementsystem;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return "Registration successful!";
    }

  @org.springframework.web.bind.annotation.PostMapping("/login")
public Student login(
        @org.springframework.web.bind.annotation.RequestBody LoginRequest request) {

    return authService.login(
            request.getEmail(),
            request.getPassword()
    );
}  

}
