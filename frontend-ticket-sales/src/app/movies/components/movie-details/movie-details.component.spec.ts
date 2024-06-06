import { TestBed } from '@angular/core/testing';
import { MovieDetailsComponent } from './movie-details.component';
import { RouterTestingModule } from "@angular/router/testing";

describe('MovieDetailsComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MovieDetailsComponent, RouterTestingModule],
    }).compileComponents();
  });

  it('should create component', () => {
    const fixture = TestBed.createComponent(MovieDetailsComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
