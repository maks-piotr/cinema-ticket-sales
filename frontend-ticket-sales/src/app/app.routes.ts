import { Routes } from '@angular/router';
import {MovieListComponent} from "./movies/components/movie-list/movie-list.component";
import {movieListResolver} from "./movies/resolvers/movie-list.resolver";
import { ScreeningListComponent } from './movies/components/screening-list/screening-list.component';
import { screeningListResolver } from './movies/resolvers/screening-list.resolver';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '/movies'
  },
  {
    path: 'movies',
    component: MovieListComponent,
    resolve: {
      movies: movieListResolver
    }
  },
  {
    path: 'screenings',
    component: ScreeningListComponent,
    resolve: {
      screenings: screeningListResolver
    }
  }
];
