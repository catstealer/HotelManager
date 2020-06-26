package com.example.demo.entity;

import com.example.demo.validators.ReservationBetweenDays;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Table(name = "reservation")
@ReservationBetweenDays
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    Long id;

    @ManyToOne
    @NotNull
    Room room;

    @OneToOne(mappedBy = "reservation")
    @JoinColumn(name = "bill_id")
    @JsonIgnore
    Bill bill;

    @Column(name = "from_date")
    @NotNull
    @FutureOrPresent
    LocalDate fromDate;

    @Column(name = "to_date")
    @NotNull
    @Future
    LocalDate toDate;

}
