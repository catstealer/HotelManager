import {Bill} from './bill';
import {User} from './user';
import {Contact} from './contact';

export interface Person {
  id: number;
  firstName: string;
  lastName: string;
  bill: Array<Bill>;
  user: User;
  contactSet: Array<Contact>;
}
