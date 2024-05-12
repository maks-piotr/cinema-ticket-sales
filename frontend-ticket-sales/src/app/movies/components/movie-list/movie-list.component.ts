import { Component } from '@angular/core';
import { Movie } from '../../model/movie';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Component({
    selector: 'bs-movie-list',
    templateUrl: './movie-list.component.html',
    styleUrls: ['./movie-list.component.css'],
    standalone: true,
    imports: [RouterLink]
})
export class MovieListComponent {

  readonly movies: Movie[];

  constructor(private readonly activatedRoute: ActivatedRoute) {
    this.movies = this.activatedRoute.snapshot.data['movies'];
    console.log(this.movies);
  }
}
