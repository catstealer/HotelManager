import {Type} from './type';
import {Tag} from './tag';

export interface Room {
  id: number;
  roomNumber: string;
  area: number;
  personAmount: number;
  description: string;
  price: number;
  roomTypeSet: Array<Type>;
  tagSet: Array<Tag>;
}
