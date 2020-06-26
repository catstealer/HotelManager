package com.example.demo.entity;


import com.example.demo.model.UserModel;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "app_user")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long id;

    @Column(unique = true)
    @Email
    @NotNull
    String email;

    @JsonIgnore
    @NotNull
    String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_role_id", referencedColumnName = "user_role_id")}
    )

    @Column(name = "user_role")
    @NotNull
    Set<UserRole> role = new HashSet<>();

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.PERSIST)
    Person person;

    public AppUser() {
        role = new HashSet<>();
    }

}
