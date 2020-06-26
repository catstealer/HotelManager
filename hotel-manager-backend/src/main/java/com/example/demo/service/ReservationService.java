package com.example.demo.service;

import com.example.demo.entity.Bill;
import com.example.demo.entity.Reservation;
import com.example.demo.model.ReservationCreateDTO;
import com.example.demo.model.ReservationDTO;
import com.example.demo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ReservationService {


    BillService billService;
    @Autowired
    public ReservationService(BillService billService) {
        this.billService = billService;
    }

    public Reservation addReservation(ReservationCreateDTO reservation, Principal principal){
         String userName = principal.getName();
         Bill bill = billService.createBillFromReservation(reservation, userName);
         return bill.getReservation();
    }
}
