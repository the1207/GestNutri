import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormuleService } from '../../services/formule.service';
import { FormuleResponse } from '../../services/formule.model';

@Component({
  selector: 'app-formule-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './formule-detail.html',
})
export class FormuleDetail implements OnInit {
  formule: FormuleResponse | null = null;
  erreur = '';

  constructor(private route: ActivatedRoute, private formuleService: FormuleService) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!Number.isInteger(id) || id <= 0) {
      this.erreur = 'Identifiant de formule invalide.';
      return;
    }

    this.formuleService.findById(id).subscribe({
      next: (formule) => (this.formule = formule),
      error: () => (this.erreur = 'Impossible de charger cette formule.'),
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
      },
      error: () => (this.erreur = `Impossible de télécharger le fichier ${type.toUpperCase()}.`),
    });
  }
}
