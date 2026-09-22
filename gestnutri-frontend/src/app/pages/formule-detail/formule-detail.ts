import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormuleService } from '../../services/formule.service';
import { FormuleResponse } from '../../services/formule.model';
import { NotificationService } from '../../services/notification.service';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { MessageModule } from 'primeng/message';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { TooltipModule } from 'primeng/tooltip';

@Component({
  selector: 'app-formule-detail',
  standalone: true,
  imports: [CommonModule, ButtonModule, CardModule, MessageModule, TableModule, TagModule, TooltipModule],
  templateUrl: './formule-detail.html',
  styleUrl: './formule-detail.css',
})
export class FormuleDetail implements OnInit {
  formule: FormuleResponse | null = null;
  erreur = '';

  constructor(
    private route: ActivatedRoute,
    private formuleService: FormuleService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!Number.isInteger(id) || id <= 0) {
      this.erreur = 'Identifiant de formule invalide.';
      return;
    }

    this.formuleService.findById(id).subscribe({
      next: (formule) => (this.formule = formule),
      error: () => {
        this.erreur = 'Impossible de charger cette formule.';
        this.notificationService.error('Formule inaccessible.');
      },
    });
  }

  telecharger(type: 'pdf' | 'csv'): void {
    if (!this.formule) {
      return;
    }

    const observable = type === 'pdf'
      ? this.formuleService.telechargerPdf(this.formule.id)
      : this.formuleService.telechargerCsv(this.formule.id);

    observable.subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const lien = document.createElement('a');
        lien.href = url;
        lien.download = `formule-${this.formule!.id}.${type}`;
        lien.click();
        window.URL.revokeObjectURL(url);
        this.notificationService.success(`Export ${type.toUpperCase()} téléchargé avec succès.`);
      },
      error: () => {
        this.erreur = `Impossible de télécharger le fichier ${type.toUpperCase()}.`;
        this.notificationService.error(`Échec du téléchargement ${type.toUpperCase()}.`);
      },
    });
  }
}
