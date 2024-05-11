import { Routes } from '@angular/router';
import {MovieListComponent} from "./movies/components/movie-list/movie-list.component";
import {movieListResolver} from "./movies/resolvers/movie-list.resolver";

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
  }
];
