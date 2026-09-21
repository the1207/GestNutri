import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { BesoinNutritionnelService } from '../../services/besoin-nutritionnel.service';
import { ProfilNutritionnelService } from '../../services/profil-nutritionnel.service';

interface LigneBesoin {
  nomNutriment: string;
  valeurMin: number | null;
  valeurMax: number | null;
}

@Component({
  selector: 'app-profil-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profil-form.html',
})
export class ProfilForm {
  nomCategorie = '';
  stade = '';
  estPersonnalise = false;

  nutriments = [
    'PROTEINES_BRUTES',
    'LYSINE',
    'METHIONINE',
    'CALCIUM',
    'PHOSPHORE',
    'SODIUM',
    'MATIERES_GRASSES',
    'CELLULOSE_BRUTE',
    'ENERGIE_METABOLISABLE',
  ];

  besoins: LigneBesoin[] = [
    { nomNutriment: this.nutriments[0], valeurMin: null, valeurMax: null },
  ];

  constructor(
    private profilNutritionnelService: ProfilNutritionnelService,
    private besoinNutritionnelService: BesoinNutritionnelService,
    private router: Router
  ) {}

  ajouterLigne(): void {
    this.besoins.push({ nomNutriment: this.nutriments[0], valeurMin: null, valeurMax: null });
  }

  supprimerLigne(index: number): void {
    this.besoins.splice(index, 1);
  }

  onSubmit(): void {
    this.profilNutritionnelService
      .create({
        nomCategorie: this.nomCategorie,
        stade: this.stade,
        estPersonnalise: this.estPersonnalise,
      })
      .subscribe((profil) => {
        this.besoins.forEach((besoin) => {
          if (besoin.valeurMin !== null && besoin.valeurMax !== null) {
            this.besoinNutritionnelService
              .create({
                ...besoin,
                valeurMin: besoin.valeurMin,
                valeurMax: besoin.valeurMax,
                profilNutritionnelId: profil.id,
              })
              .subscribe();
          }
        });
        this.router.navigate(['/formulation']);
      });
  }
}