import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
})
export class Login {
  email = '';
  motDePasse = '';
  erreur = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    this.erreur = '';
    this.authService.login({ email: this.email, motDePasse: this.motDePasse }).subscribe({
      next: () => this.router.navigate(['/dashboard']),
      error: () => (this.erreur = 'Email ou mot de passe incorrect.'),
    });
  }
}
