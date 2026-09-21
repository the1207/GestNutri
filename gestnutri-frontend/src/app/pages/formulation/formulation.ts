import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import {
  MatierePremiereResponse,
  MatierePremiereService,
} from '../../services/matiere-premiere.service';
import {
  ProfilNutritionnelResponse,
  ProfilNutritionnelService,
} from '../../services/profil-nutritionnel.service';
import {
  MoteurOptimisationResponse,
  MoteurOptimisationService,
} from '../../services/moteur-optimisation.service';

@Component({
  selector: 'app-formulation',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './formulation.html',
})
export class Formulation implements OnInit {
  matieres: MatierePremiereResponse[] = [];
  profils: ProfilNutritionnelResponse[] = [];
  matieresSelectionneesIds: number[] = [];
  profilSelectionneId: number | null = null;
  quantiteTotale: number | null = null;
  resultat: MoteurOptimisationResponse | null = null;
  chargement = false;
  erreurChargement = '';

  constructor(
    private matierePremiereService: MatierePremiereService,
    private profilNutritionnelService: ProfilNutritionnelService,
    private moteurOptimisationService: MoteurOptimisationService
  ) {}

  ngOnInit(): void {
    this.matierePremiereService.findAll().subscribe({
      next: (matieres) => (this.matieres = matieres.filter((matiere) => matiere.disponible)),
      error: () => (this.erreurChargement = 'Impossible de charger les matières premières.'),
    });
    this.profilNutritionnelService.findAll().subscribe({
      next: (profils) => (this.profils = profils),
      error: () => (this.erreurChargement = 'Impossible de charger les profils nutritionnels.'),
    });
  }

  toggleMatiere(id: number, coche: boolean): void {
    if (coche && !this.matieresSelectionneesIds.includes(id)) {
      this.matieresSelectionneesIds = [...this.matieresSelectionneesIds, id];
    } else if (!coche) {
      this.matieresSelectionneesIds = this.matieresSelectionneesIds.filter((mid) => mid !== id);
    }
  }

  onSubmit(): void {
    if (
      this.profilSelectionneId === null ||
      this.quantiteTotale === null ||
      this.quantiteTotale <= 0 ||
      this.matieresSelectionneesIds.length === 0
    ) {
      this.resultat = {
        succes: false,
        erreur: 'Sélectionnez un profil, une quantité positive et au moins une matière première.',
        formule: null,
      };
      return;
    }

    this.chargement = true;
    this.resultat = null;
    this.moteurOptimisationService
      .resoudre({
        matieresPremieresIds: this.matieresSelectionneesIds,
        profilNutritionnelId: this.profilSelectionneId,
        quantiteTotale: this.quantiteTotale,
      })
      .subscribe({
        next: (reponse) => {
          this.resultat = reponse;
          this.chargement = false;
        },
        error: (err) => {
          this.resultat = err.error ?? {
            succes: false,
            erreur: 'Erreur inattendue.',
            formule: null,
          };
          this.chargement = false;
        },
      });
  }
}
