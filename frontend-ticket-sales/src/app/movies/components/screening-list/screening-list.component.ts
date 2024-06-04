import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Screening } from '../../model/screening';
import { Movie } from '../../model/movie';
import { MoviesService } from '../../services/movies.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HallsService } from '../../services/halls.service';

@Component({
  selector: 'bs-screening-list',
  templateUrl: './screening-list.component.html',
  styleUrls: ['./screening-list.component.scss'],
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule]
})
export class ScreeningListComponent implements OnInit {
  readonly screenings: Screening[];
  filteredScreenings: Screening[];
  movies: Movie[];
  selectedDateFrom: string | null = null;
  selectedDateTo: string | null = null;
  selectedMovieId: number | null = null;

  constructor(private readonly activatedRoute: ActivatedRoute,
              private movieService: MoviesService, private hallsService: HallsService) {
    this.screenings = this.activatedRoute.snapshot.data['screenings'];
    this.filteredScreenings = [...this.screenings];
    this.movies = [];
  }

  ngOnInit() {

    this.movieService.getAllMovies().subscribe(movies => {
      this.movies = movies;
    });

    this.screenings.forEach(screening => {
      this.movieService.getMovieById(screening.movie_id).subscribe(movie => {
        screening.movie = movie;
      });
      this.hallsService.getHallById(screening.cinema_hall_id).subscribe(hall => {
        screening.cinemaHall = hall;
      });
    });

    this.activatedRoute.queryParams.subscribe(params => {
      this.selectedMovieId = +params['movieId'] || null;
      this.filterScreenings();
    });
  }

  filterScreenings() {
    this.filteredScreenings = this.screenings.filter(screening => {
      const matchesDate = (!this.selectedDateFrom || this.parseDate(screening.dateOfBeginning) >= new Date(this.selectedDateFrom)) &&
                          (!this.selectedDateTo || this.parseDate(screening.dateOfBeginning) <= new Date(this.selectedDateTo));
      const matchesMovie = !this.selectedMovieId || screening.movie_id === this.selectedMovieId;
      return matchesDate && matchesMovie;
    });
    this.sortScreenings();
  }

  sortScreenings() {
    this.filteredScreenings.sort((a, b) => {
      const dateA = this.parseDate(a.dateOfBeginning, "0:00");
      const dateB = this.parseDate(b.dateOfBeginning, "0:00");
      return dateA.getTime() - dateB.getTime();
    });
  }

  parseDate(dateString: string, timeString?: string): Date {
    const [day, month, year] = dateString.split('.').map(Number);
    if (timeString) {
      const [hour, minute] = timeString.split(':').map(Number);
      return new Date(year, month - 1, day, hour, minute);
    }
    return new Date(year, month - 1, day);
  }

  onDateChange() {
    this.filterScreenings();
  }

  onMovieChange(event: Event) {
    const select = event.target as HTMLSelectElement;
    this.selectedMovieId = parseInt(select.value, 10) || null;
    this.filterScreenings();
  }

  private convertDateFormat(dateString: string): string {
    const [year, month, day] = dateString.split('-');
    return `${day}.${month}.${year}`;
  }
}
