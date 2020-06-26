import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Type} from '../entities/type';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TypeService {

  constructor(private http: HttpClient) {
  }

  public getTypes(): Observable<Type[]> {
    return this.http.get<Type[]>(environment.linkForBackend + '/roomType');
  }
}
