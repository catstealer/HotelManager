package com.example.demo.controller;

import com.example.demo.entity.UserRole;
import com.example.demo.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class UserRoleController {
    @Autowired
    private UserRoleRepository userRoleRepository;

    @GetMapping
    public ResponseEntity<List<UserRole>> getRoles(){
        return ResponseEntity.ok(
                userRoleRepository.findAll()
        );
    }

    @PostMapping
    public ResponseEntity<UserRole> postTag(@RequestBody UserRole userRole){
        var newUserRole = userRoleRepository.save(userRole);
        return ResponseEntity.ok(
                newUserRole
        );
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteTag(@PathVariable long id){
        userRoleRepository.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }
}
