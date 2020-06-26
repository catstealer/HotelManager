import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Reservation} from '../entities/reservation';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {Room} from '../entities/room';
import {ReservationModel} from '../entities/reservation-model';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  from: Date = null;
  to: Date = null;
  room: Room = null;
  registerWanted = false;

  constructor(private http: HttpClient) {
  }

  createReservation(reservation: ReservationModel): Observable<Reservation> {
    return  this.http.post<Reservation>(environment.linkForBackend + '/reservation', reservation);
  }

}
