import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Component({
  selector: 'bs-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'ng-ticket_sales';
}
