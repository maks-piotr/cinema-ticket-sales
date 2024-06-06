import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Screening } from '../../model/screening';
import { Movie } from '../../model/movie';
import { MoviesService } from '../../services/movies.service';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HallsService } from '../../services/halls.service';

@Component({
  selector: 'bs-screening-list',
  templateUrl: './screening-list.component.html',
  styleUrls: ['./screening-list.component.css'],
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  providers: [DatePipe]
})
export class ScreeningListComponent implements OnInit {
  readonly screenings: Screening[];
  filteredScreenings: Screening[];
  movies: Movie[];
  selectedDateFrom: string | null = null;
  selectedDateTo: string | null = null;
  selectedMovieId: number | null = null;

  constructor(private readonly activatedRoute: ActivatedRoute,
              private movieService: MoviesService,
              private datePipe: DatePipe) {
    this.screenings = this.activatedRoute.snapshot.data['screenings'];
    this.filteredScreenings = [...this.screenings];
    this.movies = [];
  }

  ngOnInit() {
    this.movieService.getAllMovies().subscribe(movies => {
      this.movies = movies;
    });

    this.activatedRoute.queryParams.subscribe(params => {
      this.selectedMovieId = +params['movieId'] || null;
      this.filterScreenings();
    });
  }

  filterScreenings() {
    this.filteredScreenings = this.screenings.filter(screening => {
      const screeningDate = this.parseDate(screening.dateOfBeginning);
      const matchesDate = (!this.selectedDateFrom || screeningDate >= new Date(this.selectedDateFrom)) &&
                          (!this.selectedDateTo || screeningDate <= this.setToEndOfDay(new Date(this.selectedDateTo)));
      const matchesMovie = !this.selectedMovieId || screening.movie?.id === this.selectedMovieId;
      return matchesDate && matchesMovie;
    });
    this.sortScreenings();
  }

  sortScreenings() {
    this.filteredScreenings.sort((a, b) => {
      const dateA = this.parseDate(a.dateOfBeginning);
      const dateB = this.parseDate(b.dateOfBeginning);
      return dateA.getTime() - dateB.getTime();
    });
  }

  parseDate(dateString: string): Date {
    return new Date(dateString);
  }

  formatDate(dateString: string): string {
    return this.datePipe.transform(dateString, 'dd.MM.yyyy') || dateString;
  }
  
  formatTime(dateString: string): string {
    return this.datePipe.transform(dateString, 'HH:mm') || dateString;
  }

  setToEndOfDay(date: Date): Date {
    return new Date(date.setHours(23, 59, 59, 999));
  }

  onDateChange() {
    this.filterScreenings();
  }

  onMovieChange(event: Event) {
    const select = event.target as HTMLSelectElement;
    this.selectedMovieId = parseInt(select.value, 10) || null;
    this.filterScreenings();
  }
}
