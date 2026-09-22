import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { BesoinNutritionnelService } from '../../services/besoin-nutritionnel.service';
import { ProfilNutritionnelService } from '../../services/profil-nutritionnel.service';
import { NotificationService } from '../../services/notification.service';
import { forkJoin, of } from 'rxjs';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { CheckboxModule } from 'primeng/checkbox';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { TooltipModule } from 'primeng/tooltip';

interface LigneBesoin {
  nomNutriment: string;
  valeurMin: number | null;
  valeurMax: number | null;
}

@Component({
  selector: 'app-profil-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ButtonModule,
    CardModule,
    CheckboxModule,
    InputNumberModule,
    InputTextModule,
    SelectModule,
    TooltipModule,
  ],
  templateUrl: './profil-form.html',
  styleUrl: './profil-form.css',
})
export class ProfilForm {
  id: number | null = null;
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
    private notificationService: NotificationService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    const id = Number(this.route.snapshot.queryParamMap.get('id'));
    if (Number.isInteger(id) && id > 0) {
      this.id = id;
      this.profilNutritionnelService.findById(id).subscribe({
        next: (profil) => {
          this.nomCategorie = profil.nomCategorie;
          this.stade = profil.stade;
          this.estPersonnalise = profil.estPersonnalise;
          this.besoins = profil.besoins.map((besoin) => ({
            nomNutriment: besoin.nomNutriment,
            valeurMin: besoin.valeurMin,
            valeurMax: besoin.valeurMax,
          }));
        },
        error: () => this.notificationService.error('Profil inaccessible pour modification.'),
      });
    }
  }

  ajouterLigne(): void {
    this.besoins.push({ nomNutriment: this.nutriments[0], valeurMin: null, valeurMax: null });
  }

  supprimerLigne(index: number): void {
    this.besoins.splice(index, 1);
  }

  onSubmit(): void {
    if (!this.nomCategorie.trim() || !this.stade.trim()) {
      this.notificationService.warning('Renseignez la catégorie et le stade du profil.');
      return;
    }

    const request = {
        nomCategorie: this.nomCategorie,
        stade: this.stade,
        estPersonnalise: this.estPersonnalise,
    };

    const operation = this.id === null
      ? this.profilNutritionnelService.create(request)
      : this.profilNutritionnelService.update(this.id, request);

    operation
      .subscribe({
        next: (profil) => {
          if (this.id !== null) {
            this.notificationService.success('Profil modifié avec succès.');
            this.router.navigate(['/profils']);
            return;
          }

          const creations = this.besoins
            .filter((besoin) => besoin.valeurMin !== null && besoin.valeurMax !== null)
            .map((besoin) =>
              this.besoinNutritionnelService.create({
                ...besoin,
                valeurMin: besoin.valeurMin!,
                valeurMax: besoin.valeurMax!,
                profilNutritionnelId: profil.id,
              })
            );

          forkJoin(creations.length > 0 ? creations : [of(null)]).subscribe({
            next: () => {
              this.notificationService.success('Profil nutritionnel créé avec succès.');
              this.router.navigate(['/formulation']);
            },
            error: () => this.notificationService.error('Le profil a été créé, mais ses besoins nutritionnels n’ont pas pu être enregistrés.'),
          });
        },
        error: () => this.notificationService.error('Échec de la création du profil nutritionnel.'),
      });
  }
}