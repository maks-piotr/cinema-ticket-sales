import { Component } from '@angular/core';
import { Screening } from '../../model/screening';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Movie } from '../../model/movie';
import { MoviesService } from '../../services/movies.service';
import { HallsService } from '../../services/halls.service';
import { CommonModule } from '@angular/common';
import { Hall } from '../../model/hall';
import { Seat } from '../../model/seat';
import { SeatsService } from '../../services/seats.service';

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
  takenSeats: Seat[] = [];

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

  isTaken(row: number, seat: number): boolean {
    return this.takenSeats.some(takenSeat => takenSeat.row === row+1 && takenSeat.seat === seat+1);
  }

  constructor(private readonly activatedRoute: ActivatedRoute,
    private movieService: MoviesService,
    private hallService: HallsService,
    private seatsService: SeatsService,
    private router: Router
  ) {
    this.screening = this.activatedRoute.snapshot.data['screenings'];
    console.log(this.screening);
    if (this.screening && this.screening.movie_id) {
      const movieObserver = {
        next: (movie: Movie) => {
          this.screening.movie = movie;
        },
        error: (error: any) => {
          console.error('Error fetching movie details:', error);
        },
        complete: () => {
          console.log('Movie fetch completed');
        }
      };
    
      this.movieService.getMovieById(this.screening.movie_id).subscribe(movieObserver);
    }
    
    if (this.screening && this.screening.cinema_hall_id) {
      const hallObserver = {
        next: (hall: Hall) => {
          this.screening.hall = hall;
        },
        error: (error: any) => {
          console.error('Error fetching hall details:', error);
        },
        complete: () => {
          console.log('Hall fetch completed');
        }
      };
    
      this.hallService.getHallById(this.screening.cinema_hall_id).subscribe(hallObserver);
    }
    
    if (this.screening && this.screening.id) {
      const seatsObserver = {
        next: (seats: Seat[]) => {
          this.takenSeats = seats;
        },
        error: (error: any) => {
          console.error('Error fetching taken seats:', error);
        },
        complete: () => {
          console.log('Taken seats fetch completed');
        }
      };

      this.seatsService.getTakenSeatsByScreeningId(this.screening.id).subscribe(seatsObserver);
    }

    
    
    console.log(this.screening);

  }
  goToCheckout(): void {
    const selectedSeats = Object.keys(this.selectedSeats);
    this.router.navigate(['/checkout'], { state: { selectedSeats } });
  }
}
