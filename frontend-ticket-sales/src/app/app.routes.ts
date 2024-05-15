import { Routes } from '@angular/router';
import {MovieListComponent} from "./movies/components/movie-list/movie-list.component";
import {movieListResolver} from "./movies/resolvers/movie-list.resolver";
import { ScreeningListComponent } from './movies/components/screening-list/screening-list.component';
import { screeningListResolver } from './movies/resolvers/screening-list.resolver';
import { MovieDetailsComponent } from './movies/components/movie-details/movie-details.component';
import { movieDetailsResolver } from './movies/resolvers/movie-details.resolver';
import { ScreeningDetailsComponent } from './movies/components/screening-details/screening-details.component';
import { screeningDetailsResolver } from './movies/resolvers/screening-details.resolver';
import { CheckoutComponent } from './movies/components/checkout/checkout.component';
import { SignInComponent } from './movies/components/signin/signin.component';
import { VerifyComponent } from './movies/components/verify/verify.component';

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
  },
  {
    path: 'movies/:id/details',
    component: MovieDetailsComponent,
    resolve: {
      movies: movieDetailsResolver
    }
  },
  {
    path: 'screenings/:id',
    component: ScreeningDetailsComponent,
    resolve: {
      screenings: screeningDetailsResolver
    }
  },
  { path: 'checkout', component: CheckoutComponent },
  { path: 'signin', component: SignInComponent },
  { path: 'verification', component: VerifyComponent }
];
