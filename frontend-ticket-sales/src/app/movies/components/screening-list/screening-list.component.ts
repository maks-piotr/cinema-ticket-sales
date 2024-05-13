import { Component } from '@angular/core';
import { Screening } from '../../model/screening';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Movie } from '../../model/movie';
import { MoviesService } from '../../services/movies.service';
import { BrowserModule } from '@angular/platform-browser'
import { CommonModule } from '@angular/common';

@Component({
    selector: 'bs-screening-list',
    templateUrl: './screening-list.component.html',
    styleUrls: ['./screening-list.component.scss'],
    standalone: true,
    imports: [CommonModule, RouterLink]
})
export class ScreeningListComponent {

  readonly screenings: Screening[];

  constructor(private readonly activatedRoute: ActivatedRoute,
    private movieService: MoviesService
  ) {
    this.screenings = this.activatedRoute.snapshot.data['screenings'];
    this.screenings.forEach(screening => {
        this.movieService.getMovieById(screening.movie_id).subscribe(movie => {
          screening.movie = movie;
        });
    });
    console.log(this.screenings);

  }
}
