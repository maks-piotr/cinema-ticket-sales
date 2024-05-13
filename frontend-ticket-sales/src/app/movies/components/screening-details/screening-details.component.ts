import { Component } from '@angular/core';
import { Screening } from '../../model/screening';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Movie } from '../../model/movie';
import { MoviesService } from '../../services/movies.service';
import { HallsService } from '../../services/halls.service';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'bs-screening-details',
    templateUrl: './screening-details.component.html',
    styleUrls: ['./screening-details.component.css'],
    standalone: true,
    imports: [RouterLink, CommonModule]
})
export class ScreeningDetailsComponent {

  readonly screening: Screening;
  selectedSeats: any = {};

  generateArray(num: number): number[] {
    return Array(num).fill(0).map((x, i) => i);
  }

  selectSeat(row: number, seat: number): void {
    const key = `${row}-${seat}`;
    if (this.selectedSeats[key]) {
      delete this.selectedSeats[key];
    } else {
      this.selectedSeats[key] = true;
    }
  }

  isSelected(row: number, seat: number): boolean {
    return !!this.selectedSeats[`${row}-${seat}`];
  }

  constructor(private readonly activatedRoute: ActivatedRoute,
    private movieService: MoviesService,
    private hallService: HallsService
  ) {
    this.screening = this.activatedRoute.snapshot.data['screenings'];
    console.log(this.screening);
    if (this.screening && this.screening.movie_id) {
      this.movieService.getMovieById(this.screening.movie_id).subscribe(
        movie => {
          this.screening.movie = movie;
        },
        error => {
          console.error('Error fetching movie details:', error);
        }
      );
    }
    if (this.screening && this.screening.movie_id) {
      this.hallService.getHallById(this.screening.cinema_hall_id).subscribe(
        hall => {
          this.screening.hall = hall;
        },
        error => {
          console.error('Error fetching movie details:', error);
        }
      );
    }
    
    console.log(this.screening);

  }
}
