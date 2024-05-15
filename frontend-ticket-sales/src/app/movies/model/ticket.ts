import { Seat } from "./seat";

export interface Ticket {
    verification_code: string;
    screening_id: number;
    state: string;
    seats: Seat[];
  }