import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Screening } from '../model/screening';

const screeningsApiPrefix = '/api/screenings';

@Injectable({
  providedIn: 'root'
})
export class ScreeningsService {

  constructor(private readonly http: HttpClient) { }

  getAllScreenings(): Observable<Screening[]> {
    return this.http.get<Screening[]>(screeningsApiPrefix);
  }
  getScreeningById(screeningId: number): Observable<Screening> {
    return this.http.get<Screening>(`${screeningsApiPrefix}/${screeningId}`);
  }
}
