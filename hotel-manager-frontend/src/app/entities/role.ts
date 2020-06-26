import {User} from './user';

export interface Role {
  id: number;
  name: string;
  description: string;
  user: Array<User>;
}
