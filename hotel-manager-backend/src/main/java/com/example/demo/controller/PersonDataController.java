package com.example.demo.controller;

import com.example.demo.entity.Bill;
import com.example.demo.entity.Contact;
import com.example.demo.entity.Person;
import com.example.demo.model.ContactModel;
import com.example.demo.model.PersonDTO;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.BillService;
import com.example.demo.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Set;

import static com.example.demo.config.Messages.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/people")
public class PersonDataController {

    //    @Autowired
//    public PersonDataController(ContactService contactService) {
//        this.contactService = contactService;
//    }
//
//    @Transactional
//    @PostMapping("/contact")
//    public ResponseEntity<?> createContact(@RequestBody @Valid ContactModel contact){
//        log.info(makeMessageForController("POST [/contact]"));
//        Contact newContact = contactService.addContact(contact);
//        Link link = new Link("/contact/"+ newContact.getId());
//        return ResponseEntity.created(link.toUri()).build();
//    }
    @Autowired
    PersonRepository personRepository;
    @Autowired
    BillRepository billRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable long id) {
        var person = personRepository.findById(id);
        return ResponseEntity.ok(
                person.orElseThrow(IllegalArgumentException::new)
        );
    }

    @GetMapping
    public ResponseEntity<Page<Person>> getPeople(Pageable pageable) {
        var personPage = personRepository.findAll(pageable);
        return ResponseEntity.ok(personPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@RequestBody PersonDTO personDTO, @PathVariable long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Person with this id doesn't exits")
                );
        person.setLastName(personDTO.getLastName());
        person.setFirstName(personDTO.getFirstName());
        var newPerson = personRepository.save(person);
        return ResponseEntity.ok(newPerson);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deletePerson(@PathVariable long id) {
        personRepository.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }

    @GetMapping("/{id}/bills")
    public ResponseEntity<List<Bill>> getBillsOfPerson(@PathVariable long id){
        List<Bill> bills = billRepository.findAllByTenantId(id);
       return ResponseEntity.ok(bills);

    }
}
