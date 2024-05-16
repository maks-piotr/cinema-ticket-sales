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

    const signinObserver = {
      next: (ticket: Ticket) => {
        if (ticket) {
          this.ticket = ticket;
          this.errorMessage = null;
        } else {
          this.errorMessage = 'Invalid ticket code. Please try again.';
          this.ticket = null;
        }
      },
      error: (error: any) => {
        this.errorMessage = 'Invalid ticket code. Please try again.';
        this.ticket = null;
      },
      complete: () => {
        console.log('Verification completed');
      }
    };

    this.ticketService.verifyTicket(this.ticketCode).subscribe(signinObserver)
  }
}
