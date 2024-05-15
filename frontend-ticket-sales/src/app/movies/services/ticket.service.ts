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

  verifyTicket(ticketCode: string): Observable<Ticket> {
    return this.http.get<Ticket[]>(`${ticketApiPrefix}?verification_code=${ticketCode}`).pipe(
      tap(response => console.log('API Response:', response)), // This logs the raw response
      map(response => {
        if (response && response.length > 0) {
            return response[0]; // Return the first ticket if the array is not empty
        }
        throw new Error('No tickets found'); // Throw an error if the array is empty
      }),
      catchError(error => {
        console.error('API Error or No tickets found:', error);
        return throwError(() => new Error('Failed to verify ticket or no tickets available')); // Emit an error observable
      })
    );
}
}


