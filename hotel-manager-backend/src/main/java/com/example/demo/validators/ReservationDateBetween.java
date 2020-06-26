package com.example.demo.validators;

import com.example.demo.entity.Reservation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReservationDateBetween implements ConstraintValidator<ReservationBetweenDays, Reservation> {
   public void initialize(ReservationBetweenDays constraint) {
   }

   public boolean isValid(Reservation obj, ConstraintValidatorContext context) {
      LocalDate fromDate = obj.getFromDate();
      LocalDate toDate = obj.getToDate();
      return fromDate.isBefore(toDate) || fromDate.isEqual(toDate);
   }
}
