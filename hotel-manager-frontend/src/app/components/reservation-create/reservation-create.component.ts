import {Component, OnInit} from '@angular/core';
import {ReservationService} from '../../service/reservation.service';
import {Room} from '../../entities/room';
import {ReservationModel} from '../../entities/reservation-model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-reservation-create',
  templateUrl: './reservation-create.component.html',
  styleUrls: ['./reservation-create.component.css']
})
export class ReservationCreateComponent implements OnInit {

  room: Room;
  from: Date;
  to: Date;

  constructor(private reservationService: ReservationService, private router: Router) {
  }

  ngOnInit(): void {
    this.from = this.reservationService.from;
    this.to = this.reservationService.to;
    this.room = this.reservationService.room;
  }

  formatDate(date: Date): string {
    return date.toLocaleDateString();
  }

  estimatePrice(): string {
    return (this.daysBetweenDates(this.from, this.to) * this.room.price).toFixed(2);
  }

  daysBetweenDates(from: Date, to: Date) {
    return ((to.getTime() - from.getTime()) / (1000 * 3600 * 24));
  }

  ngOnSubmit(): void {

    if (localStorage.getItem('userValue')) {
      const reservation: ReservationModel = {
        roomId: this.room.id,
        from: this.from.toISOString().split('T')[0],
        to: this.to.toISOString().split('T')[0],
      };

      this.reservationService.createReservation(reservation)
        .subscribe(newReservation => {
          console.log(newReservation);
          this.from = null;
          this.to = null;
          this.room = null;
          this.router.navigate(['/reservation', newReservation.id]);
        });
    } else {
      this.router.navigate(['/register']);
    }
  }
}
