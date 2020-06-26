package com.example.demo.controller;

import com.example.demo.entity.Contact;
import com.example.demo.model.ContactDTO;
import com.example.demo.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin
@RestController
@RequestMapping("/contacts")
public class ContactController {
    @Autowired
    ContactRepository contactRepository;

    @GetMapping
    public ResponseEntity<Page<Contact>> getContact(Pageable pageable){
        var page = contactRepository.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable long id){
        var bill = contactRepository.findById(id)
                .orElseThrow(
                        IllegalArgumentException::new
                );
        return ResponseEntity.ok(bill);
    }

    @PostMapping
    public ResponseEntity<Contact> postContact(@RequestBody Contact contact){
        var newContact = contactRepository.save(contact);
        URI uri = linkTo(methodOn(BillController.class).getBill(newContact.getId())).toUri();
        return ResponseEntity
                .created(uri)
                .body(newContact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(@RequestBody ContactDTO contact, @PathVariable long id){
        Contact contactFormDb =  contactRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cotnact with this id doesn't exist"));
        contactFormDb.setPhoneNumber(contact.getPhoneNumber());
        var newContact = contactRepository.save(contactFormDb);
        return ResponseEntity.ok(newContact);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteContact(@PathVariable long id){
        contactRepository.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }
}
