import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ActivatedRoute } from '@angular/router';
import { ScreeningListComponent } from './screening-list.component';
import { MoviesService } from '../../services/movies.service';
import { DatePipe } from '@angular/common';
import { of } from 'rxjs';
import { Screening } from '../../model/screening';
import { Movie } from '../../model/movie';

describe('ScreeningListComponent', () => {
  let component: ScreeningListComponent;
  let fixture: ComponentFixture<ScreeningListComponent>;

  const mockScreenings: Screening[] = [
    {
      id: 1,
      dateOfBeginning: '2024-06-15T14:30:00',
      movie: {
        id: 1,
        title: "The Lord of the Rings: The Fellowship of the Ring (2001)",
        director: "Peter Jackson",
        description: "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.",
        lengthInMins: 120,
        photoUrl: "https://m.media-amazon.com/images/M/MV5BN2EyZjM3NzUtNWUzMi00MTgxLWI0NTctMzY4M2VlOTdjZWRiXkEyXkFqcGdeQXVyNDUzOTQ5MjY@._V1_FMjpg_UX1000_.jpg"
        }
    },
    {
        id: 2,
        dateOfBeginning: '2024-07-15T14:30:00',
        movie: {
          id: 1,
          title: "The Lord of the Rings: The Fellowship of the Ring (2001)",
          director: "Peter Jackson",
          description: "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.",
          lengthInMins: 120,
          photoUrl: "https://m.media-amazon.com/images/M/MV5BN2EyZjM3NzUtNWUzMi00MTgxLWI0NTctMzY4M2VlOTdjZWRiXkEyXkFqcGdeQXVyNDUzOTQ5MjY@._V1_FMjpg_UX1000_.jpg"
          }
    },
    {
        id: 3,
        dateOfBeginning: '2024-06-15T14:30:00',
        movie : {
            id: 2,
            title: "The Place Beyond the Pines (2012)",
            director: "Derek Cianfrance",
            description: "Two men and their sons must deal with the unforeseen consequences of their actions.",
            lengthInMins: 140,
            photoUrl: "https://m.media-amazon.com/images/M/MV5BMjc1OTEwNjU4N15BMl5BanBnXkFtZTcwNzUzNDIwOQ@@._V1_FMjpg_UX1000_.jpg"
        }
    },
];

  const mockMovies: Movie[] = [
    {
        id: 1,
        title: "The Lord of the Rings: The Fellowship of the Ring (2001)",
        director: "Peter Jackson",
        description: "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.",
        lengthInMins: 120,
        photoUrl: "https://m.media-amazon.com/images/M/MV5BN2EyZjM3NzUtNWUzMi00MTgxLWI0NTctMzY4M2VlOTdjZWRiXkEyXkFqcGdeQXVyNDUzOTQ5MjY@._V1_FMjpg_UX1000_.jpg"
    },
    {
        id: 2,
        title: "The Place Beyond the Pines (2012)",
        director: "Derek Cianfrance",
        description: "Two men and their sons must deal with the unforeseen consequences of their actions.",
        lengthInMins: 140,
        photoUrl: "https://m.media-amazon.com/images/M/MV5BMjc1OTEwNjU4N15BMl5BanBnXkFtZTcwNzUzNDIwOQ@@._V1_FMjpg_UX1000_.jpg"
    }
  ];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, ScreeningListComponent],
      providers: [
        DatePipe,
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              data: {
                screenings: mockScreenings
              }
            },
            queryParams: of({ movieId: 1 })
          }
        },
        {
          provide: MoviesService,
          useValue: {
            getAllMovies: () => of(mockMovies)
          }
        }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ScreeningListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create component', () => {
    expect(component).toBeTruthy();
  });

  it('should format date correctly', () => {
    const formattedDate = component.formatDate('2024-06-15T14:30:00');
    expect(formattedDate).toBe('15.06.2024');
  });

  it('should format time correctly', () => {
    const formattedTime = component.formatTime('2024-06-15T14:30:00');
    expect(formattedTime).toBe('14:30');
  });

  it('should filter screenings by movie and date', () => {
    component.selectedMovieId = 1;
    component.selectedDateFrom = '2024-06-14';
    component.selectedDateTo = '2024-06-16';
    component.filterScreenings();
    expect(component.filteredScreenings.length).toBe(1);
  });

  it('should sort screenings by date', () => {
    const screeningsToSort = [
      { dateOfBeginning: '2024-06-16T14:30:00' },
      { dateOfBeginning: '2024-06-15T14:30:00' }
    ];
    component.filteredScreenings = screeningsToSort as Screening[];
    component.sortScreenings();
    expect(component.filteredScreenings[0].dateOfBeginning).toBe('2024-06-15T14:30:00');
  });
});
