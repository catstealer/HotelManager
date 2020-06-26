package com.example.demo;

import com.example.demo.entity.Room;
import com.example.demo.entity.RoomType;
import com.example.demo.entity.Tag;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class FakeObjectsFactory {

    public List<Room> getFakeListOfRooms(){
        List<Room> fakeList = new LinkedList<>();
        Tag tag = new Tag(0L,"Widok na morze", new HashSet<>());
        Tag tag2 = new Tag(1L,"Widok na góry", new HashSet<>());

        RoomType roomType = new RoomType(0L, "Apartament", new HashSet<>());
        RoomType roomType2 = new RoomType(1L, "Zwykły", new HashSet<>());
        Room fakeRoom = Room.builder()
                .roomNumber("1025")
                .area(20)
                .id(0L)
                .price(100.)
                .description("Najlepszy 1 osobowy pokój w stosuynku jakosc/cena.")
                .personAmount(1)
                .reservationSet(new HashSet<>())
                .roomTypeSet(new HashSet<>())
                .build();

        Room fakeRoom2 = Room.builder()
                .roomNumber("1024")
                .area(50)
                .id(1L)
                .price(200.)
                .description("Najlepszy 3 osobowy pokój w stosuynku jakosc/cena.")
                .personAmount(3)
                .reservationSet(new HashSet<>())
                .roomTypeSet(new HashSet<>())
                .build();

        fakeRoom.getTagSet().add(tag);
        fakeRoom.getTagSet().add(tag2);

        fakeRoom.getRoomTypeSet().add(roomType);

        fakeRoom2.getTagSet().add(tag);
        fakeRoom2.getTagSet().add(tag2);

        fakeRoom2.getRoomTypeSet().add(roomType2);

        fakeList.add(fakeRoom);
        fakeList.add(fakeRoom2);
        return fakeList;
    }


}

