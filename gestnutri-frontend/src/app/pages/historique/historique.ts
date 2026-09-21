import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormuleService } from '../../services/formule.service';
import { FormuleResponse } from '../../services/formule.model';

@Component({
  selector: 'app-historique',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './historique.html',
})
export class Historique implements OnInit {
  formules: FormuleResponse[] = [];
  erreur = '';

  constructor(private formuleService: FormuleService) {}

  ngOnInit(): void {
    this.formuleService.findAll().subscribe({
      next: (formules) => (this.formules = formules),
      error: () => (this.erreur = 'Impossible de charger l’historique des formules.'),
    });
  }
}
