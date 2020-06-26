package com.example.demo.controller;

import com.example.demo.entity.AppUser;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{email}")
    public ResponseEntity<AppUser> getUser(@PathVariable String email) {
        return ResponseEntity.ok(
                userRepository.findUserByEmail(email).orElseThrow(
                        IllegalArgumentException::new
                )
        );
    }

    @PostMapping
    public ResponseEntity<AppUser> postUser(@RequestBody AppUser appUser) {
        return ResponseEntity.ok(
                userRepository.save(appUser)
        );
    }

    @PutMapping("/{email}")
    public ResponseEntity<AppUser> putUser(@RequestBody UserModel appUser, @PathVariable String email) {
        Optional<AppUser> userOptional = userRepository.findUserByEmail(email);
        AppUser appUserFromDb = userOptional
                .orElseThrow(
                        () -> new IllegalArgumentException("This user doesn't exist")
                );
        appUserFromDb.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUserFromDb.setEmail(appUser.getEmail());
        var newUser = userRepository.save(appUserFromDb);
        return ResponseEntity.ok(newUser);
    }
}
