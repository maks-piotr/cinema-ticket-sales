import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { MoviesService } from '../services/movies.service';
import { Movie } from '../model/movie';

export const movieDetailsResolver: ResolveFn<Movie> = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
): Observable<Movie> => {
  const id = route.params['id'];
  if (id === null) {
    return throwError(() => new Error('Book ID is required'));
  }
  return inject(MoviesService).getMovieById(id);
};