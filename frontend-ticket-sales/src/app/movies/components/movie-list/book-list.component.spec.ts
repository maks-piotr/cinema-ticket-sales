import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieListComponent } from './movie-list.component';
import {ActivatedRoute} from "@angular/router";

describe('MovieListComponent', () => {
  let component: MovieListComponent;
  let fixture: ComponentFixture<MovieListComponent>;
  let activatedRouteMock: any;

  beforeEach(() => {
    activatedRouteMock = {
      snapshot: {
        data: {
          movies: []
        }
      }
    };
  });

  beforeEach(async () => {
    await TestBed.configureTestingModule({
    imports: [MovieListComponent],
    providers: [
        { provide: ActivatedRoute, useValue: activatedRouteMock }
    ]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MovieListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
