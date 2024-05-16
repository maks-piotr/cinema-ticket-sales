import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Ticket } from '../model/ticket';

const ticketApiPrefix = '/api/tickets';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  constructor(private http: HttpClient) {}

  verifyTicket(verificationCode: string): Observable<Ticket> {
    return this.http.get<Ticket[]>(`${ticketApiPrefix}?verification_code=${verificationCode}`).pipe(
      map(response => {
        if (response && response.length > 0) {
          return response[0];
        }
        throw new Error('No tickets found');
      }),
      catchError(error => {
        return throwError(() => new Error('Failed to verify ticket or no tickets available'));
      })
    );
  }
}



