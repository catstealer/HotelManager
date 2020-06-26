import {Person} from './person';
import {Role} from './role';

export interface User {
  id: number;
  email: string;
  person: Person;
  role: Role[];
}
