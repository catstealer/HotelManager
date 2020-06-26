package com.example.demo.service;

import com.example.demo.entity.Room;
import com.example.demo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.example.demo.specification.RoomSpecification.*;
import static com.example.demo.specification.SpecParams.*;

@Service
public class RoomService {

    RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getRoomList() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow();
    }

    public Page<Room> getPageOfRoomWithSearching(Pageable pageable, Specification<Room> roomSpecification) {
        return roomRepository.findAll(roomSpecification, pageable);
    }

    public Specification<Room> getSpecififcationFromParams(MultiValueMap<String, String> params) {
        Specification<Room> specification = Specification.where(null);
        for (String key : params.keySet()) {
            if (key.equals(FROM_DATE.toString())) {
                LocalDate localDateTimeFrom = LocalDate.parse(params.get(FROM_DATE.toString()).get(0));
                LocalDate localDateTimeTo = LocalDate.parse(params.get(TO_DATE.toString()).get(0));
                specification = specification.and(betweenDate(localDateTimeFrom, localDateTimeTo));
            } else if (key.equals(AREA.toString())) {
                int area = Integer.parseInt(params.get(AREA.toString()).get(0));
                specification = specification.and(hasAreaEqualTo(area));
            } else if (key.equals(PERSON_AMOUNT.toString())) {
                int personAmount = Integer.parseInt(params.get(PERSON_AMOUNT.toString()).get(0));
                specification = specification.and(hasPersonAmountEqualTo(personAmount));
            } else if (key.equals(TAGS.toString())) {
                List<String> tags = params.get(TAGS.toString());
                specification = specification.and(hasTag(tags));
            } else if (key.equals(TYPES.toString())) {
                List<String> types = params.get(TYPES.toString());
                specification = specification.and(isType(types));
            }
        }
        return specification;
    }
}
