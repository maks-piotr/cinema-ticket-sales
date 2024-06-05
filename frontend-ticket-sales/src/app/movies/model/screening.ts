import { Hall } from "./hall";
import { Movie } from "./movie";

export interface Screening {
    id: number;
    dateOfBeginning: string;
    movie?: Movie;
    cinemaHall?: Hall;
}
