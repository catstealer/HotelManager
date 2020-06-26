import {Component, OnInit} from '@angular/core';
import {faPlus, faSearch} from '@fortawesome/free-solid-svg-icons';
import {RoomService} from '../../service/room.service';
import {Reservation} from '../../entities/reservation';
import {Router} from '@angular/router';
import {consoleTestResultHandler} from 'tslint/lib/test';
import {Room} from '../../entities/room';
import {ReservationService} from '../../service/reservation.service';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})
export class RoomComponent implements OnInit {

  faPlus = faPlus;
  roomId: number;
  roomReservations: Reservation[] = [];
  room: Room;

  constructor(private roomService: RoomService,
              private router: Router,
              private reservationService: ReservationService) {
  }

  ngOnInit(): void {
    this.roomId = +this.router.url.split('/').pop();
    this.roomService.getOneRoom(this.roomId).subscribe(room => this.room = room);
    this.roomService.getRoomReservations(this.roomId)
      .subscribe(value => this.roomReservations = value);
  }


  dateSelect(reservationDate: {from: Date, to: Date}) {
    this.reservationService.room = this.room;
    this.reservationService.to = reservationDate.to;
    this.reservationService.from = reservationDate.from;
  }

  ngOnSubmit(){
    this.router.navigate(['/reservation', 'create']);
  }

}
