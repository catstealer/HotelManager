import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Page} from '../entities/page';
import {Observable} from 'rxjs';
import {Room} from '../entities/room';
import {environment} from '../../environments/environment';
import {RoomSerching} from './room-serching';
import {map} from 'rxjs/operators';
import {Reservation} from '../entities/reservation';

@Injectable({
  providedIn: 'root',
})
export class RoomService {

  constructor(private http: HttpClient) {
  }

  public getPageOfRooms(page: { size: number; page: number }, filters: RoomSerching): Observable<{ roomList: Room[], page: Page }> {
    let params = new HttpParams();
    const paramsObj = {...page, ...filters};
    for (const param in paramsObj) {
      if (paramsObj.hasOwnProperty(param) && paramsObj[param] && (paramsObj[param]?.length || typeof paramsObj[param] === 'number' )) {
        params = params.append(param, paramsObj[param]);
      }
    }
    return this.http.get<any>(environment.linkForBackend + '/rooms', {params})
      .pipe(map(
        json => {
          return {roomList: json?._embedded?.roomList, page: json.page};
        }
      ));
  }

  public getRoomReservations(id: number): Observable<Reservation[]>{
    return this.http.get<Reservation[]>(environment.linkForBackend + `/rooms/${id}/reservations`);
  }

  public postRoom(room: Room): Observable<Room> {
    return this.http.post<Room>(environment.linkForBackend + '/rooms', room);
  }

  public deleteRoom(id: number): void {
    this.http.delete(environment.linkForBackend + '/rooms/' + id).subscribe();
  }

  public getOneRoom(id: number): Observable<Room> {
    return this.http.get<Room>(environment.linkForBackend + '/rooms/' + id);
  }

}
