import { TestBed } from '@angular/core/testing';

import { movieListResolver } from './movie-list.resolver';
import {MoviesService} from "../services/movies.service";
import {ResolveFn} from "@angular/router";

describe('MovieListResolver', () => {
  const movieListResolver: ResolveFn<boolean> = (...resolverParameters) =>
    TestBed.runInInjectionContext(() => movieListResolver(...resolverParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(movieListResolver).toBeTruthy();
  });
});
