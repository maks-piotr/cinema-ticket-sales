import { Hall } from "./hall";
import { Movie } from "./movie";

export interface Screening {
    id: number;
    cinema_hall_id: number;
    movie_id: number;
    hour_of_beginning: string;
    date_of_beginning: string;
    movie?: Movie;
    hall?: Hall;
}
