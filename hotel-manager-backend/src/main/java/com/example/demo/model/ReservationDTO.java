package com.example.demo.model;

import com.example.demo.entity.Bill;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.Room;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ReservationDTO {
    Long id;
    String room;
    Bill bill;
    LocalDate fromDate;
    LocalDate toDate;

    public static ReservationDTO makeFromEntity(Reservation reservation){

        return ReservationDTO.builder()
                .bill(reservation.getBill())
                .fromDate(reservation.getFromDate())
                .toDate(reservation.getToDate())
                .id(reservation.getId())
                .room(reservation.getRoom().getId().toString())
                .build();
    }
}
