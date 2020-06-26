package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    Long id;

    @Column(name = "phone_number")
    @NotNull
    @Size(min = 9, max = 9)
    String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @NotNull
    @JsonBackReference(value = "contact-set")
    Person person;

    public Contact(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

}
