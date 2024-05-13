import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule, Location } from '@angular/common';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
  standalone: true,
  imports: [RouterLink, CommonModule]
})
export class CheckoutComponent implements OnInit {
  selectedSeats: string[] = [];
  reservationCode: string | null = null;
  isButtonDisabled = false;

  constructor(private route: ActivatedRoute, private location: Location) {}

  ngOnInit(): void {
    const state = this.location.getState() as { selectedSeats: string[] };
    if (state && state.selectedSeats) {
      this.selectedSeats = state.selectedSeats.map(seat => {
        const [row, seatNumber] = seat.split('-').map(Number);
        return `Row ${row + 1}, Seat ${seatNumber + 1}`;
      });
    }
  }

  pay(): void {
    this.reservationCode = '000-000-001';
    this.isButtonDisabled = true;
  }
}