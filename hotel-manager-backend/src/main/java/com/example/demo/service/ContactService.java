package com.example.demo.service;

import com.example.demo.entity.Contact;
import com.example.demo.entity.Person;
import com.example.demo.model.ContactModel;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    ContactRepository contactRepository;
    PersonRepository personRepository;



    @Autowired
    public ContactService(ContactRepository contactRepository, PersonRepository personRepository) {
        this.contactRepository = contactRepository;
        this.personRepository = personRepository;
    }


    public Contact addContact(ContactModel contactModel){
        Contact contact = new Contact();
        if(contactModel.personId != null){
            Person person = personRepository.findById(contactModel.getPersonId()).orElseThrow();
            contact.setPerson(person);
        }
        contact.setPhoneNumber(contactModel.getPhoneNumber());
        return contactRepository.save(contact);
    }
}
