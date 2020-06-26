package com.example.demo.controller;

import com.example.demo.config.Messages;
import com.example.demo.entity.Bill;
import com.example.demo.entity.Reservation;
import com.example.demo.model.ReservationCreateDTO;
import com.example.demo.model.ReservationDTO;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ReservationService;
import com.sun.mail.iap.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/reservation")
public class ReservationController {


    private ReservationService reservationService;
    private ReservationRepository reservationRepository;
    private BillRepository billRepository;
    private UserRepository userRepository;
    private PersonRepository personRepository;

    @Autowired
    public ReservationController(ReservationService reservationService, ReservationRepository reservationRepository, BillRepository billRepository, UserRepository userRepository, PersonRepository personRepository) {
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
        this.billRepository = billRepository;
        this.userRepository = userRepository;
        this.personRepository = personRepository;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Reservation> makeReservation(@RequestBody ReservationCreateDTO reservation, Principal principal) {
        log.info(Messages.makeMessageForController("POST [/reservation]"));
        Reservation addedEntity = reservationService.addReservation(reservation, principal);
        return ResponseEntity.ok(addedEntity);
    }

    @GetMapping
    public ResponseEntity<Page<Reservation>> getReservations(Pageable pageable) {
        return ResponseEntity.ok(reservationRepository.findAll(pageable));
    }

    @CrossOrigin
    @GetMapping("/person/{id}/bills")
    public ResponseEntity<List<Bill>> getPersonBills(@PathVariable long id) {
        return ResponseEntity.ok(billRepository.findAllByTenantId(id));
    }

    //TODO wczytywanie nie swoich danych można sprawdzać ifem jak tu lub napisać jakiś 'sprytny' Interceptor

    @GetMapping("/bills/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable long id, Principal principal) {
        var person = userRepository.findUserByEmail(principal.getName())
                .orElseThrow(IllegalArgumentException::new)
                .getPerson();
        var bill = billRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        if(!person.getId().equals(bill.getTenant().getId())){
            throw new IllegalArgumentException();
        }
        return ResponseEntity.ok(bill);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable long id) {
        var reservation = reservationRepository.findById(id)
                .orElseThrow(
                        IllegalArgumentException::new
                );

        return ResponseEntity.ok(reservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable long id, Reservation reservation) {
        if (!reservationRepository.existsById(id))
            throw new IllegalArgumentException("No reservation with this id");
        var newReservation = reservationRepository.save(reservation);
        return ResponseEntity.ok(newReservation);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteReservation(@PathVariable long id) {
        reservationRepository.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    void handleException(IllegalArgumentException ex) {

    }

}
