package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "room")
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    Long id;

    @Column(name = "room_number")
    @NotNull
    @Size(min = 1, max = 5)
    String roomNumber;

    @Column(name = "area")
    @Min(20)
    @Max(100)
    @NotNull
    Integer area;

    @Column(name = "person_amount")
    @Min(1)
    @Max(5)
    @NotNull
    Integer personAmount;

    @NotNull
    @Size(min = 24)
    @NotNull
    String description;

    @Column(name = "room_price")
    @Min(1)
    @NotNull
    Double price;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    Set<Reservation> reservationSet = new HashSet<>();

    @ManyToMany
    @BatchSize(size = 10)
    @JoinTable(
            joinColumns = {@JoinColumn(name = "room_id", referencedColumnName = "room_id")},
            inverseJoinColumns = {@JoinColumn(name = "room_type_id", referencedColumnName = "room_type_id")}
    )
    Set<RoomType> roomTypeSet = new HashSet<>();

    @ManyToMany
    @BatchSize(size = 10)
    @JoinTable(
            joinColumns = {@JoinColumn(name = "room_id", referencedColumnName = "room_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "tag_id")}
    )
    Set<Tag> tagSet = new HashSet<>();



}
