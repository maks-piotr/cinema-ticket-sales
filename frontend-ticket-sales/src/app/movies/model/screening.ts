import { Hall } from "./hall";
import { Movie } from "./movie";

export interface Screening {
    id: number;
    cinema_hall_id: number;
    movie_id: number;
    dateOfBeginning: string;
    movie?: Movie;
    cinemaHall?: Hall;
}
