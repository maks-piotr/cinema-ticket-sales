import {inject} from '@angular/core';
import {ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot} from '@angular/router';
import {MoviesService} from '../services/movies.service';
import {Movie} from '../model/movie';

export const movieListResolver: ResolveFn<Movie[]> = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot,
) => {
  return inject(MoviesService).getAllMovies();
};
