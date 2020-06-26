package com.example.demo.controller;

import com.example.demo.entity.Room;
import com.example.demo.exceptions.ApiError;
import com.example.demo.model.ReservationDTO;
import com.example.demo.repository.RoomRepository;
import com.example.demo.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.config.Messages.makeMessageForController;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/rooms")
public class RoomController {

    private RoomService roomService;
    private RoomRepository roomRepository;
    private PagedResourcesAssembler<Room> pagedResourcesAssembler;


    @Autowired
    public RoomController(RoomService roomService, RoomRepository roomRepository, PagedResourcesAssembler<Room> pagedResourcesAssembler) {
        this.roomService = roomService;
        this.roomRepository = roomRepository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Room>>> getListOfRooms(Pageable pageable, @RequestParam MultiValueMap<String, String> params) {
        log.info(makeMessageForController("GET [/rooms]"));
        Specification<Room> specification = roomService.getSpecififcationFromParams(params);
        Page<Room> roomPage = roomService.getPageOfRoomWithSearching(pageable, specification);
        PagedModel<EntityModel<Room>> pagedModel = pagedResourcesAssembler.toModel(roomPage);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping(path = "/{id}/reservations")
    public ResponseEntity<List<ReservationDTO>> getRoomReservation(@PathVariable Long id){
        var room = roomRepository.findById(id)
                .orElseThrow(() -> {throw new IllegalArgumentException("There is no room with this Id");});
        var reservations = room.getReservationSet().stream()
                .map(ReservationDTO::makeFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable long id){
        var room = roomRepository.findById(id)
                .orElseThrow(
                        IllegalArgumentException::new
                );
        return ResponseEntity.ok(room);
    }

    @PostMapping
    public ResponseEntity<Room> postRoom(@RequestBody Room room){
        var newRoom = roomRepository.save(room);
        return ResponseEntity.ok(newRoom);
    }

    @ExceptionHandler({DateTimeParseException.class})
    public ResponseEntity<ApiError> handleDateParseException(DateTimeParseException e){
        ApiError apiError =  buildApiError(e.getMessage(), "Date should be passed as YY-MM-DD string");
        return ResponseEntity.badRequest()
                .body(apiError);
    }

    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<ApiError> handleNumberFormatException(NumberFormatException e){
        ApiError apiError = buildApiError(e.getMessage(), "Filed for number cannot contain characters");
        return ResponseEntity.badRequest()
                .body(apiError);
    }

    private ApiError buildApiError(String excMessage, String debugMessage){
        return ApiError.builder()
                .debugMessage(debugMessage)
                .message(excMessage)
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

}
