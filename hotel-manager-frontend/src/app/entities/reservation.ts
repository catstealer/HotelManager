import {Room} from './room';
import {Bill} from './bill';

export interface Reservation {
  id: number;
  room: Room;
  bill: Bill;
  fromDate: Date;
  toDate: Date;
}
