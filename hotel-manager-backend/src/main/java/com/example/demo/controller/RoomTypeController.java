package com.example.demo.controller;

import com.example.demo.entity.RoomType;
import com.example.demo.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/roomType")
public class RoomTypeController {

    @Autowired
    RoomTypeRepository roomTypeRepository;

    @GetMapping
    public ResponseEntity<List<RoomType>> getRoomTypes(){
        var roomTypes = roomTypeRepository.findAll();
        return ResponseEntity.ok(roomTypes);
    }

    @PostMapping
    public ResponseEntity<RoomType> postRoomType(@RequestBody RoomType roomType){
        var newRoomType = roomTypeRepository.save(roomType);
        return ResponseEntity.ok(newRoomType);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteRoomType(@PathVariable long id){
        roomTypeRepository.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomType> putRoomType(@RequestBody RoomType roomType, @PathVariable long id){
        if(!roomTypeRepository.existsById(id))
            throw new IllegalArgumentException("RoomType with this id doesn't exists");
        var newRoomType = roomTypeRepository.save(roomType);
        return ResponseEntity.ok(newRoomType);
    }

}
