import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormuleService } from '../../services/formule.service';
import { FormuleResponse } from '../../services/formule.model';
import { NotificationService } from '../../services/notification.service';
import { ButtonModule } from 'primeng/button';
import { MessageModule } from 'primeng/message';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { TooltipModule } from 'primeng/tooltip';

@Component({
  selector: 'app-historique',
  standalone: true,
  imports: [CommonModule, RouterLink, ButtonModule, MessageModule, TableModule, TagModule, TooltipModule],
  templateUrl: './historique.html',
  styleUrl: './historique.css',
})
export class Historique implements OnInit {
  formules: FormuleResponse[] = [];
  erreur = '';

  constructor(private formuleService: FormuleService, private notificationService: NotificationService) {}

  ngOnInit(): void {
    this.formuleService.findAll().subscribe({
      next: (formules) => (this.formules = formules),
      error: () => {
        this.erreur = 'Impossible de charger l’historique des formules.';
        this.notificationService.error('Historique des formules inaccessible.');
      },
    });
  }

  supprimer(formule: FormuleResponse): void {
    if (!window.confirm(`Supprimer la formule ${formule.id} ?`)) {
      return;
    }

    this.formuleService.delete(formule.id).subscribe({
      next: () => {
        this.formules = this.formules.filter((item) => item.id !== formule.id);
        this.notificationService.success('Formule supprimée avec succès.');
      },
      error: () => this.notificationService.error('Échec de la suppression de la formule.'),
    });
  }
}
