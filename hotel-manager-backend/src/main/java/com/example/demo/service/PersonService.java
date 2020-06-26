package com.example.demo.service;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Contact;
import com.example.demo.entity.Person;
import com.example.demo.model.PersonModel;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class PersonService {


    PersonRepository personRepository;
    private UserRepository userRepository;


    @Autowired
    public PersonService(PersonRepository personRepository, UserRepository userRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }

    public Set<Contact> getContactOfPerson(Long personId) {
        Optional<Person> personOptional = personRepository.findById(personId);
        Person person = personOptional.orElseThrow();

        return person.getContactSet();
    }


    public Person addPerson(PersonModel personModel){
        Person person = new Person();
        if(personModel.getUserEmail() != null){
            AppUser appUser = userRepository.findUserByEmail(personModel.getUserEmail()).orElseThrow();
            person.setAppUser(appUser);
        }
        person.setFirstName(personModel.getFirstName());
        person.setLastName(personModel.getLastName());
       return personRepository.save(person);
    }

}

