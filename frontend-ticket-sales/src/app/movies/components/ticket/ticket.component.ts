import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Ticket } from '../../model/ticket';

@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrls: ['./ticket.component.css'],
  standalone: true,
  imports: [CommonModule]
})
export class TicketComponent {
  @Input() ticket!: Ticket;
  isButtonDisabled = false;

  onButtonClick(): void {
    this.isButtonDisabled = true;
  }
}