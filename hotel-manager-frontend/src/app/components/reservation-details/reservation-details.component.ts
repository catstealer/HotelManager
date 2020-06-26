import {Component, OnInit} from '@angular/core';
import {Reservation} from '../../entities/reservation';
import {Room} from '../../entities/room';
import {Bill} from '../../entities/bill';
import {Person} from '../../entities/person';
import {User} from '../../entities/user';
import {Contact} from '../../entities/contact';
import {Type} from '../../entities/type';
import {Tag} from '../../entities/tag';
import {Router} from '@angular/router';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-reservation-details',
  templateUrl: './reservation-details.component.html',
  styleUrls: ['./reservation-details.component.css']
})
export class ReservationDetailsComponent implements OnInit {

  bill: Bill;
  billId: number;

  constructor(private http: HttpClient, private router: Router) {
  }

  ngOnInit(): void {
    this.billId = +this.router.url.split('/').pop();
    console.log(this.billId);
    this.getBill().then(x => this.bill = x);
  }

  async getBill(): Promise<Bill> {
    return await this.http.get<Bill>(environment.linkForBackend + '/reservation/bills/' + this.billId).toPromise();
  }
}
