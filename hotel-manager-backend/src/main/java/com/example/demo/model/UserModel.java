package com.example.demo.model;

import com.example.demo.entity.Contact;
import com.example.demo.entity.Person;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserModel {
    @Email
    @NotNull
    String email;
    @NotNull
    @Size(min = 5, max = 24)
    String password;
    @NotNull
    @Size(min = 9, max = 9)
    String contact;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
}
