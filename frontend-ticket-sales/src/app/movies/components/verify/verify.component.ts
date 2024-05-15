// verify.component.ts
import { Component } from '@angular/core';
import { TicketService } from '../../services/ticket.service';
import { Ticket } from '../../model/ticket';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TicketComponent } from '../ticket/ticket.component';

@Component({
  selector: 'app-verify',
  templateUrl: './verify.component.html',
  styleUrls: ['./verify.component.css'],
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule, ReactiveFormsModule, TicketComponent]
})
export class VerifyComponent {
  ticketCode: string = '';
  ticket: any = null;
  errorMessage: string | null = null;

  constructor(private ticketService: TicketService) {}

  verifyTicket(): void {
    this.ticketService.verifyTicket(this.ticketCode).subscribe(
      (ticket) => {
        console.log(ticket)
        if (ticket) {
          this.ticket = ticket;
          this.errorMessage = null;
        } else {
          this.errorMessage = 'Invalid ticket code. Please try again.';
          this.ticket = null;
        }
      },
      (error) => {
        this.errorMessage = 'An error occurred. Please try again.';
        this.ticket = null;
      }
    );
  }
}
