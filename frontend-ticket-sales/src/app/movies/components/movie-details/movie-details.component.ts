import { Component } from '@angular/core';
import { Movie } from '../../model/movie';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Component({
    selector: 'bs-movie-details',
    templateUrl: './movie-details.component.html',
    styleUrls: ['./movie-details.component.css'],
    standalone: true,
    imports: [RouterLink]
})
export class MovieDetailsComponent {

  readonly movie: Movie;

  constructor(private readonly activatedRoute: ActivatedRoute) {
    this.movie = this.activatedRoute.snapshot.data['movies'];
    console.log(this.movie);
  }
}
