import {Component, OnInit} from '@angular/core';
import {Reservation} from '../../entities/reservation';
import {Room} from '../../entities/room';
import {Bill} from '../../entities/bill';
import {Type} from '../../entities/type';
import {Tag} from '../../entities/tag';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {UserService} from '../../service/user.service';
import {User} from '../../entities/user';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.css']
})

export class ReservationsComponent implements OnInit {

  arrayBill: Bill[];

  constructor(private http: HttpClient, private userService: UserService) {
  }

  ngOnInit(): void {
    this.getUserReservations();
  }

  async getUserReservations() {
    let id: number;
    const username = this.userService.currentUserSubject.getValue().username;
    if (this.userService.currentUserDataSubject.getValue()) {
      id = this.userService.currentUserDataSubject.getValue().person.id;
    } else {
      id = (await this.userService.getUserInfo(username).toPromise()).person.id;
    }
    this.arrayBill = await this.http.get<Bill[]>(environment.linkForBackend + '/reservation/person/' + id + '/bills').toPromise();

  }
}
