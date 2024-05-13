import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { ScreeningsService } from '../services/screenings.service';
import { Movie } from '../model/movie';
import { Screening } from '../model/screening';

export const screeningDetailsResolver: ResolveFn<Screening> = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
): Observable<Screening> => {
  const id = route.params['id'];
  if (id === null) {
    return throwError(() => new Error('Book ID is required'));
  }
  return inject(ScreeningsService).getScreeningById(id);
};