import {Reservation} from './reservation';
import {Person} from './person';
import {User} from './user';

export interface Bill {
  id: number;
  price: number;
  reservation: Reservation;
  tenant: Person;
}
