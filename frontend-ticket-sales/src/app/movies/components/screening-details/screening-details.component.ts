import { Component } from '@angular/core';
import { Screening } from '../../model/screening';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Movie } from '../../model/movie';
import { MoviesService } from '../../services/movies.service';
import { HallsService } from '../../services/halls.service';
import { CommonModule, DatePipe } from '@angular/common';
import { Hall } from '../../model/hall';
import { Seat } from '../../model/seat';
import { SeatsService } from '../../services/seats.service';

@Component({
    selector: 'bs-screening-details',
    templateUrl: './screening-details.component.html',
    styleUrls: ['./screening-details.component.css'],
    standalone: true,
    imports: [RouterLink, CommonModule],
    providers: [DatePipe]
})
export class ScreeningDetailsComponent {

  readonly screening: Screening;
  selectedSeats: any = {};
  takenSeats: Seat[] = [];

  generateArray(num?: number): number[] {
    return num ? Array(num).fill(0).map((_, i) => i) : [];
  }

  selectSeat(row: number, seat: number): void {
    const key = `${row}-${seat}`;
    if (this.selectedSeats[key]) {
      delete this.selectedSeats[key];
    } else if(!this.takenSeats.some(s => s.screening_id === this.screening.id && s.row === row+1 && s.seat === seat+1)) {
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
    private router: Router,
    private datePipe: DatePipe
  ) {
    this.screening = this.activatedRoute.snapshot.data['screenings'];
    console.log(this.screening);
    
  
    // need new method of getting taken seats after switching to real backend

    // if (this.screening && this.screening.id) {
    //   const seatsObserver = {
    //     next: (seats: Seat[]) => {
    //       this.takenSeats = seats;
    //     },
    //     error: (error: any) => {
    //       console.error('Error fetching taken seats:', error);
    //     },
    //     complete: () => {
    //       console.log('Taken seats fetch completed');
    //     }
    //   };

    //   this.seatsService.getTakenSeatsByScreeningId(this.screening.id).subscribe(seatsObserver);
    // }

    
    
    console.log(this.screening);

  }
  goToCheckout(): void {
    if (Object.keys(this.selectedSeats).length > 0) {
      const selectedSeats = Object.keys(this.selectedSeats);
      this.router.navigate(['/checkout'], { state: { selectedSeats } });
    }
  }
  formatDate(dateString: string): string {
    return this.datePipe.transform(dateString, 'dd.MM.yyyy') || dateString;
  }
  
  formatTime(dateString: string): string {
    return this.datePipe.transform(dateString, 'HH:mm') || dateString;
  }

}
