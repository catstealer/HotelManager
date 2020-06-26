import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Statistics} from '../entities/statistics';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

  constructor(private http: HttpClient) {
  }

  public getStatistics(): Observable<Statistics> {
    return this.http.get<Statistics>(environment.linkForBackend + '/bills/statistics');
  }
}
