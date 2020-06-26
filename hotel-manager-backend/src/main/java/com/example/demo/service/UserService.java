package com.example.demo.service;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Contact;
import com.example.demo.entity.Person;
import com.example.demo.entity.UserRole;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserRoleRepository userRoleRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    public AppUser addNewUser(UserModel userModel) throws UserAlreadyExistsException {
        Optional<AppUser> userOptional = this.userRepository.findUserByEmail(userModel.getEmail());
        if(userOptional.isPresent())
            throw new UserAlreadyExistsException(userModel.getEmail());

        Contact contact = new Contact(userModel.getContact());

        Person person = Person.builder()
                .contactSet(Set.of(contact))
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .build();

        AppUser appUser = AppUser.builder()
                .email(userModel.getEmail())
                .password(userModel.getPassword())
                .person(person)
                .role(new HashSet<>())
                .build();

        contact.setPerson(person);
        person.setAppUser(appUser);

        encodeUserPassword(appUser);

        addDefaultRoleToUser(appUser.getRole());

        return this.userRepository.save(appUser);
    }

    private void encodeUserPassword(AppUser appUser){
        String password = appUser.getPassword();
        String passwordEncoded = passwordEncoder.encode(password);
        appUser.setPassword(passwordEncoded);
    }

    private void addDefaultRoleToUser(Set<UserRole> userRoleSet){
        UserRole defaultRole = userRoleRepository.findUserRoleByName("USER").get();
        userRoleSet.add(defaultRole);
    }

}
