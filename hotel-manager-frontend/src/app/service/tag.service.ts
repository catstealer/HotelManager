import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Tag} from '../entities/tag';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TagService {

  constructor(private http: HttpClient) {
  }

  public getTags(): Observable<Tag[]> {
    return this.http.get<Tag[]>(environment.linkForBackend + '/tag');
  }
}

