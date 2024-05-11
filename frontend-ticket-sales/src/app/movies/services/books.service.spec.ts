import { TestBed } from '@angular/core/testing';

import { MoviesService } from './movies.service';
import {HttpClient} from "@angular/common/http";

describe('MoviesService', () => {
  let service: MoviesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        { provide: HttpClient, useValue: {} }
      ]
    });
    service = TestBed.inject(MoviesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
