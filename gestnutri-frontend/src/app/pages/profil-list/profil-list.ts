import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { MessageModule } from 'primeng/message';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { TooltipModule } from 'primeng/tooltip';
import {
  ProfilNutritionnelResponse,
  ProfilNutritionnelService,
} from '../../services/profil-nutritionnel.service';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-profil-list',
  standalone: true,
  imports: [CommonModule, RouterLink, ButtonModule, CardModule, MessageModule, TableModule, TagModule, TooltipModule],
  templateUrl: './profil-list.html',
  styleUrl: './profil-list.css',
})
export class ProfilList implements OnInit {
  profils: ProfilNutritionnelResponse[] = [];
  profilVoir: ProfilNutritionnelResponse | null = null;
  erreur = '';

  constructor(
    private profilService: ProfilNutritionnelService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.charger();
  }

  charger(): void {
    this.profilService.findAll().subscribe({
      next: (profils) => (this.profils = profils),
      error: () => {
        this.erreur = 'Impossible de charger les profils nutritionnels.';
        this.notificationService.error('Profils nutritionnels inaccessibles.');
      },
    });
  }

  voir(profil: ProfilNutritionnelResponse): void {
    this.profilVoir = this.profilVoir?.id === profil.id ? null : profil;
  }

  supprimer(profil: ProfilNutritionnelResponse): void {
    if (!window.confirm(`Supprimer le profil ${profil.nomCategorie} ?`)) {
      return;
    }

    this.profilService.delete(profil.id).subscribe({
      next: () => {
        this.profils = this.profils.filter((item) => item.id !== profil.id);
        this.profilVoir = null;
        this.notificationService.success('Profil supprimé avec succès.');
      },
      error: () => this.notificationService.error('Échec de la suppression du profil.'),
    });
  }
}
