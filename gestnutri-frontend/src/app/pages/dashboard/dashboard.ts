import { Component } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { NotificationService } from '../../services/notification.service';
import { ButtonModule } from 'primeng/button';
import { ToolbarModule } from 'primeng/toolbar';
import { TooltipModule } from 'primeng/tooltip';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterLink, RouterOutlet, ButtonModule, ToolbarModule, TooltipModule],
  templateUrl: './dashboard.html',
})
export class Dashboard {
  constructor(
    private authService: AuthService,
    private notificationService: NotificationService,
    private router: Router
  ) {}

  deconnecter(): void {
    this.authService.logout();
    this.notificationService.success('Déconnexion réussie.');
    this.router.navigate(['/login']);
  }
}
