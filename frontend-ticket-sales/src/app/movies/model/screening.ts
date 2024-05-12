import { Movie } from "./movie";

export interface Screening {
    id: number;
    cinema_hall: string;
    movie_id: number;
    hour_of_beginning: string;
    date_of_beginning: string;
    movie: Movie;
}
