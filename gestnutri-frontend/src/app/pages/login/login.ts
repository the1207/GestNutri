import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { NotificationService } from '../../services/notification.service';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { MessageModule } from 'primeng/message';
import { PasswordModule } from 'primeng/password';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonModule, CardModule, InputTextModule, MessageModule, PasswordModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  email = '';
  motDePasse = '';
  erreur = '';

  constructor(
    private authService: AuthService,
    private notificationService: NotificationService,
    private router: Router
  ) {}

  onSubmit(): void {
    this.erreur = '';
    this.authService.login({ email: this.email, motDePasse: this.motDePasse }).subscribe({
      next: () => {
        this.notificationService.success('Connexion réussie. Bienvenue dans GestNutri.');
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        this.erreur = 'Email ou mot de passe incorrect.';
        this.notificationService.error('Connexion échouée : vérifiez vos identifiants.');
      },
    });
  }
}
