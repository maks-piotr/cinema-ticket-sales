import { TestBed } from '@angular/core/testing';
import { ScreeningDetailsComponent } from './screening-details.component';
import { RouterTestingModule } from '@angular/router/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

describe('ScreeningDetailsComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, ScreeningDetailsComponent],
      providers: [
        DatePipe,
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              data: {
                screenings: {
                  id: 1,
                  date: '2024-06-15T14:30:00',
                  movie: {
                    id: 1,
                    title: 'Test Movie',
                    description: 'Test Description',
                    director: 'Test Director',
                    releaseDate: '2024-01-01'
                  }
                }
              }
            }
          }
        }
      ]
    }).compileComponents();
  });

  it('should create component', () => {
    const fixture = TestBed.createComponent(ScreeningDetailsComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should format date correctly', () => {
    const fixture = TestBed.createComponent(ScreeningDetailsComponent);
    const app = fixture.componentInstance;
    const formattedDate = app.formatDate('2024-06-15T14:30:00');
    expect(formattedDate).toBe('15.06.2024');
  });

  it('should format time correctly', () => {
    const fixture = TestBed.createComponent(ScreeningDetailsComponent);
    const app = fixture.componentInstance;
    const formattedTime = app.formatTime('2024-06-15T14:30:00');
    expect(formattedTime).toBe('14:30');
  });
});
