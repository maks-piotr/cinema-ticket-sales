import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-sign-in',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css'],
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
    
    const signinObserver = {
      next: (feedback: any) => {
        this.router.navigate(['/verification']);
        console.log('Sign-in successful');
        this.errorMessage = null
      },
      error: (error: any) => {
        this.errorMessage = 'Sign-in failed. Please check your credentials and try again.';
        console.error('Sign-in failed', error);
      },
      complete: () => {
        console.log('Signin completed');
      }
    };

    this.authService.signIn(this.email, this.password).subscribe(signinObserver)
}
}
