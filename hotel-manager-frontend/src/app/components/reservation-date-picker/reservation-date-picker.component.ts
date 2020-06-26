import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NgbCalendar, NgbDate, NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {faSearch} from '@fortawesome/free-solid-svg-icons';
import {Reservation} from '../../entities/reservation';

@Component({
  selector: 'app-reservation-date-picker',
  templateUrl: './reservation-date-picker.component.html',
  styleUrls: ['./reservation-date-picker.component.css']
})
export class ReservationDatePickerComponent implements OnInit {

  hoveredDate: NgbDate | null = null;

  fromDate: NgbDate | null;
  toDate: NgbDate | null;

  @Input()
  reservations: Reservation[] = [];

  @Output()
  selectedDate = new EventEmitter<{ from: Date, to: Date }>();

  constructor(private calendar: NgbCalendar, private formatter: NgbDateParserFormatter) {
  }

  ngOnInit(): void {
  }

  onDateSelection(date: NgbDate) {
    if (date.before(this.calendar.getToday())) {
      return;
    }
    if (!this.fromDate && !this.toDate) {
      this.fromDate = date;
    } else if (this.fromDate && !this.toDate && date.after(this.fromDate)) {
      this.toDate = date;
      if (this.validChoosedReservation()) {
        const from = this.convertNgbDate(this.fromDate);
        const to = this.convertNgbDate(this.toDate);
        this.selectedDate.emit({from, to});
        return;
      } else {
        this.toDate = null;
        this.fromDate = null;
      }
    } else {
      this.toDate = null;
      this.fromDate = date;
    }
  }

  convertNgbDate(date: NgbDate): Date {
    return new Date(date.year, date.month - 1, date.day);
  }

  validChoosedReservation(): boolean {
    return !this.checkIfReservationIsInRange(this.fromDate, this.toDate);
  }

  isHovered(date: NgbDate) {
    return this.fromDate && !this.toDate && this.hoveredDate && date.after(this.fromDate) && date.before(this.hoveredDate);
  }

  isInside(date: NgbDate) {
    return this.toDate && date.after(this.fromDate) && date.before(this.toDate);
  }

  isRange(date: NgbDate) {
    return date.equals(this.fromDate) || (this.toDate && date.equals(this.toDate)) || this.isInside(date) || this.isHovered(date);
  }

  checkIfReservationIsInRange(fromDate: NgbDate, toDate: NgbDate): boolean {
    const checkDate = (date) => this.chechIfDateIsInRange(this.formatter.format(fromDate), this.formatter.format(toDate), date);
    const res = this.reservations.find(reservation => checkDate(reservation.toDate) || checkDate(reservation.fromDate));
    return Boolean(res);
  }


  checkIfDateAvailable = (date: NgbDate, current?: { year: number, month: number }): boolean => {
    const jsDate = new Date(date.year, date.month - 1, date.day);
    const res = this.reservations.find(reservation => this.checkIfDateOverlapsReservation(jsDate, reservation));
    return Boolean(res);
  };

  checkIfDateOverlapsReservation(date: Date, reservation: Reservation): boolean {
    return this.chechIfDateIsInRange(reservation.fromDate, reservation.toDate, date);
  }

  chechIfDateIsInRange(fromDate: Date | string, toDate: Date | string, date: Date | string): boolean {
    if (typeof fromDate === 'string') {
      fromDate = new Date(fromDate);
      fromDate.setHours(0, 0, 0);
    }
    if (typeof toDate === 'string') {
      toDate = new Date(toDate);
      toDate.setHours(0, 0, 0);
    }
    if (typeof date === 'string') {
      date = new Date(date);
      date.setHours(0, 0, 0);
    }
    return fromDate <= date && date <= toDate;
  }


}
