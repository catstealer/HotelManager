package com.example.demo.controller;

import com.example.demo.entity.AppUser;
import com.example.demo.exceptions.ApiError;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.model.UserModel;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Controller
@CrossOrigin( origins = "*")
public class AuthController {

    UserService userService;
    ContactRepository contactRepository;
    UserRepository userRepository;
    PersonRepository personRepository;

    @Autowired
    public AuthController(UserService userService, ContactRepository contactRepository, PersonRepository personRepository,  UserRepository userRepository) {
        this.userService = userService;
        this.contactRepository = contactRepository;
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserModel user) {
        log.info("Received request POST [/register]");
        try {
            AppUser createdUser = userService.addNewUser(user);
        } catch (UserAlreadyExistsException exception) {
            return ResponseEntity
                    .status(CONFLICT)
                    .body(exception.getMessage());
        }

        return ResponseEntity
                .status(CREATED)
                .build();
    }


    @PostMapping("/auth")
    public ResponseEntity<AppUser> authenticate(Principal principal) {
        String userName = principal.getName();
        AppUser user = userRepository.findUserByEmail(userName)
                .orElseThrow(IllegalArgumentException::new);
        return ResponseEntity.ok(user);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(UNAUTHORIZED)
    public ResponseEntity<?> handleNumberFormatException(NumberFormatException e){
        return null;
    }
}
