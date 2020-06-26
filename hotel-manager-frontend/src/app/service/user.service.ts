import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {LoginCredentials} from '../entities/login-credentials';
import {map, tap} from 'rxjs/operators';
import {UserModel} from '../entities/user-model';
import {User} from '../entities/user';
import {UserEdit} from '../entities/user-edit';
import {Person} from '../entities/person';
import {Contact} from '../entities/contact';
import {PersonEdit} from '../entities/person-edit';
import {ContactEdit} from '../entities/contact-edit';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public currentUserSubject: BehaviorSubject<LoginCredentials>;
  public currentUser: Observable<LoginCredentials>;

  public currentUserDataSubject: BehaviorSubject<User>;
  public currentUserData: Observable<User>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<LoginCredentials>(JSON.parse(localStorage.getItem('userValue')));
    this.currentUser = this.currentUserSubject.asObservable();
    this.currentUserDataSubject = new BehaviorSubject<User>(null);
    this.currentUserData = this.currentUserDataSubject.asObservable();
  }

  public get userValue(): LoginCredentials {
    return this.currentUserSubject.value;
  }

  login(username: string, password: string): Observable<LoginCredentials> {
    const httpHeaders = new HttpHeaders({Authorization: 'Basic ' + window.btoa(username + ':' + password)});
    return this.http.post<any>(`${environment.linkForBackend}/auth`, {}, {headers: httpHeaders})
      .pipe(map(val => {
        const user: LoginCredentials = {
          token: window.btoa(username + ':' + password),
          username
        };
        this.currentUserDataSubject.next(val);
        localStorage.setItem('userValue', JSON.stringify(user));
        this.currentUserSubject.next(user);
        return user;
      }));
  }

  register(newUser: UserModel): Observable<any> {
    return this.http.post(environment.linkForBackend + '/register', newUser);
  }

  logout() {
    localStorage.removeItem('userValue');
    this.currentUserSubject.next(null);
    this.currentUserDataSubject.next(null);
  }

  editUser(userEdit: UserEdit, username: string): Observable<User> {
    return this.http.put<User>(environment.linkForBackend + '/user/' + username, userEdit);
  }

  editPerson(person: PersonEdit, id: number): Observable<Person> {
    return this.http.put<Person>(environment.linkForBackend + '/people/' + id, person);
  }

  editContact(contact: ContactEdit, id: number): Observable<Contact> {
    return this.http.put<Contact>(environment.linkForBackend + '/contacts/' + id, contact);
  }

  getUserInfo(username: string): Observable<User> {
    return this.http.get<User>(environment.linkForBackend + '/user/' + username)
      .pipe(
        tap(val => this.currentUserDataSubject.next(val))
      );
  }

  public isAdmin(): boolean {
    const user = this.currentUserDataSubject.getValue();
    let flag = false;
    if (user) {
      user.role.forEach(rol => {
        if (rol.name === 'ADMIN') {
          flag = true;
        }
      });
    }
    return flag;
  }

}
