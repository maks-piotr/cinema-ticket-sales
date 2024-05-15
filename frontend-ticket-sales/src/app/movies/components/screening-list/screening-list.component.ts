import { Component } from '@angular/core';
import { Screening } from '../../model/screening';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Movie } from '../../model/movie';
import { MoviesService } from '../../services/movies.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'bs-screening-list',
  templateUrl: './screening-list.component.html',
  styleUrls: ['./screening-list.component.scss'],
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule]
})
export class ScreeningListComponent {
  readonly screenings: Screening[];
  filteredScreenings: Screening[];
  movies: Movie[];
  selectedDate: string | null = null;
  selectedMovieId: number | null = null;

  constructor(private readonly activatedRoute: ActivatedRoute,
              private movieService: MoviesService) {
    this.screenings = this.activatedRoute.snapshot.data['screenings'];
    this.filteredScreenings = [...this.screenings];
    

    this.activatedRoute.queryParams.subscribe(params => {
      this.selectedMovieId = +params['movieId'] || null;
      this.filterScreenings();
    });

    this.screenings.forEach(screening => {
      
      this.movieService.getMovieById(screening.movie_id).subscribe(movie => {
        screening.movie = movie;
      });
    });

    this.movies = [];
    this.movieService.getAllMovies().subscribe(movies => {
      this.movies = movies;
    });
  }

  filterScreenings() {
    this.filteredScreenings = this.screenings.filter(screening => {
      const matchesDate = !this.selectedDate || screening.date_of_beginning === this.convertDateFormat(this.selectedDate);
      console.log(screening.date_of_beginning)
      if (this.selectedDate != null) {
      console.log(this.convertDateFormat(this.selectedDate))
      }
      const matchesMovie = !this.selectedMovieId || screening.movie_id === this.selectedMovieId;
      return matchesDate && matchesMovie;
    });
  }

  onDateChange(event: Event) {
    const input = event.target as HTMLInputElement;
    const date = new Date(input.value);
    //this.selectedDate = this.formatDate(date);
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
