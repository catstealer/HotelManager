package com.example.demo.specification;

import com.example.demo.entity.Reservation;
import com.example.demo.entity.Room;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import java.time.LocalDate;
import java.util.List;

public class RoomSpecification {

    public static Specification<Room> betweenDate(LocalDate from, LocalDate to) {
        return (Specification<Room>) (root, query, criteriaBuilder) -> {
            Join<Room, Reservation> roomReservertionJoin = root.join("reservationSet", JoinType.LEFT);
            return criteriaBuilder.or(
                    criteriaBuilder.greaterThan(roomReservertionJoin.get("fromDate"), to),
                    criteriaBuilder.lessThan(roomReservertionJoin.get("toDate"), from),
                    criteriaBuilder.and(
                          criteriaBuilder.isNull(roomReservertionJoin.get("fromDate")),
                          criteriaBuilder.isNull(roomReservertionJoin.get("toDate"))
                    )
            );
        };
    }

    public static Specification<Room> isType(final List<String> roomTypeList) {
        return (Specification<Room>) (root, query, criteriaBuilder) -> {
            Path<String> roomTypePath = root.join("roomTypeSet", JoinType.LEFT).get("name");
            return roomTypePath.in(roomTypeList);
        };
    }

    public static Specification<Room> hasTag(final List<String> roomTag) {
        return (Specification<Room>) (root, query, criteriaBuilder) -> {
            Path<String> roomTypePath = root.join("tagSet", JoinType.LEFT).get("name");
            return roomTypePath.in(roomTag);
        };
    }

    public static Specification<Room> hasAreaEqualTo(int area) {
        return (Specification<Room>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("area"), area);
    }

    public static Specification<Room> hasPersonAmountEqualTo(int personAmount) {
        return (Specification<Room>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("personAmount"), personAmount);
    }
}

