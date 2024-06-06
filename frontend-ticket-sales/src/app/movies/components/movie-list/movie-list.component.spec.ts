import { TestBed } from '@angular/core/testing';
import { MovieListComponent } from './movie-list.component';
import { RouterTestingModule } from "@angular/router/testing";

describe('MovieListComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MovieListComponent, RouterTestingModule],
    }).compileComponents();
  });

  it('should create component', () => {
    const fixture = TestBed.createComponent(MovieListComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
