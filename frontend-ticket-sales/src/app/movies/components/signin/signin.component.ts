import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-sign-in',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss'],
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule, ReactiveFormsModule]
})
export class SignInComponent {
  email: string = '';
  password: string = '';
  errorMessage: string | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  signIn(event: Event) {
    event.preventDefault();
    
    this.authService.signIn(this.email, this.password).subscribe(() => {
      this.router.navigate(['/verification']);
      console.log('Sign-in successful');
      this.errorMessage = null
    }, (error: any) => {
      this.errorMessage = 'Sign-in failed. Please check your credentials and try again.';
      console.error('Sign-in failed', error);
    });
  }
}
