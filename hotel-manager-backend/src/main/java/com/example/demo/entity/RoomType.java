package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "room_type")
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_type_id")
    Long id;

    @Column(unique = true)
    @NotNull
    @Size(min = 3, max = 20)
    String name;


    @JsonIgnore
    @ManyToMany(mappedBy = "roomTypeSet")
    Set<Room> roomSet;

    public RoomType() {
    }

    public RoomType(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
