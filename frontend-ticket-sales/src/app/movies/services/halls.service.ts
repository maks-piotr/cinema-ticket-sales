import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Hall } from '../model/hall';

const hallsApiPrefix = '/api/cinema_halls';

@Injectable({
  providedIn: 'root'
})
export class HallsService {

  constructor(private readonly http: HttpClient) { }

  getAllHalls(): Observable<Hall[]> {
    return this.http.get<Hall[]>(hallsApiPrefix);
  }
  getHallById(hall_id: number): Observable<Hall> {
    return this.http.get<Hall>(`${hallsApiPrefix}/${hall_id}`);
  }
}
