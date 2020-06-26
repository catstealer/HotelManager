package com.example.demo.entity;


import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "person")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    Long id;

    @Column(name = "first_name")
    @NotNull
    @Size(min = 3, max = 32)
    String firstName;

    @Column(name = "last_name")
    @NotNull
    @Size(min = 3, max = 32)
    String lastName;

    @JsonBackReference
    @OneToMany(mappedBy = "tenant")
    Set<Bill> bills;

    @JsonBackReference
    @OneToOne(mappedBy = "person")
    @NotNull
    AppUser appUser;

    @JsonManagedReference
    @OneToMany(mappedBy = "person",cascade = CascadeType.PERSIST)
    Set<Contact> contactSet;

    public  Person(Long id){
        this.id = id;
    }

    public Person(@NotNull @Size(min = 3, max = 32) String firstName, @NotNull @Size(min = 3, max = 32) String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(){
        this.contactSet = new HashSet<>();
        this.bills = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

}
