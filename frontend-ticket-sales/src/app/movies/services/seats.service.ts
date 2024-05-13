import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Seat } from '../model/seat';

const seatsApiPrefix = '/api/taken_seats';

@Injectable({
  providedIn: 'root'
})
export class SeatsService {

  constructor(private readonly http: HttpClient) { }

  getAllTakenSeats(): Observable<Seat[]> {
    return this.http.get<Seat[]>(seatsApiPrefix);
  }
  getTakenSeatsByScreeningId(seat_id: number): Observable<Seat[]> {
    return this.http.get<Seat[]>(`${seatsApiPrefix}?screening_id=${seat_id}`);
  }
}
