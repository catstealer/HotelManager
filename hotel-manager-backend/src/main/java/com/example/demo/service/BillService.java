package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.model.ReservationCreateDTO;
import com.example.demo.model.ReservationDTO;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class BillService {


    BillRepository billRepository;
    ReservationRepository reservationRepository;
    UserRepository userRepository;
    RoomRepository roomRepository;

    @Autowired
    public BillService(BillRepository billRepository, ReservationRepository reservationRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.billRepository = billRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public Bill createBillFromReservation(ReservationCreateDTO reservation, String name) throws NoSuchElementException{
        AppUser appUser = userRepository.findUserByEmail(name)
                .orElseThrow(IllegalArgumentException::new);

        Person person = appUser.getPerson();

        Room room = roomRepository.findById(reservation.getRoomId())
                .orElseThrow(IllegalArgumentException::new);

        Reservation reservationPersisted = Reservation.builder()
                .fromDate(reservation.getFrom())
                .toDate(reservation.getTo())
                .room(room)
                .build();

        long days = ChronoUnit.DAYS.between(reservation.getFrom(),reservation.getTo());

        double price = room.getPrice() * days;

        Bill bill = Bill.builder()
                .price(price)
                .reservation(reservationPersisted)
                .tenant(person)
                .date(LocalDate.now())
                .build();

        reservationPersisted.setBill(bill);

        billRepository.save(bill);
        return bill;
    }


}
