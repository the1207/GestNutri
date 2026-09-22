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
import { NotificationService } from '../../services/notification.service';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { CheckboxModule } from 'primeng/checkbox';
import { InputNumberModule } from 'primeng/inputnumber';
import { MessageModule } from 'primeng/message';
import { SelectModule } from 'primeng/select';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { TooltipModule } from 'primeng/tooltip';

@Component({
  selector: 'app-formulation',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    ButtonModule,
    CardModule,
    CheckboxModule,
    InputNumberModule,
    MessageModule,
    SelectModule,
    TableModule,
    TagModule,
    TooltipModule,
  ],
  templateUrl: './formulation.html',
  styleUrl: './formulation.css',
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
    private moteurOptimisationService: MoteurOptimisationService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.matierePremiereService.findAll().subscribe({
      next: (matieres) => (this.matieres = matieres.filter((matiere) => matiere.disponible)),
      error: () => {
        this.erreurChargement = 'Impossible de charger les matières premières.';
        this.notificationService.error('Matières premières inaccessibles.');
      },
    });
    this.profilNutritionnelService.findAll().subscribe({
      next: (profils) => (this.profils = profils),
      error: () => {
        this.erreurChargement = 'Impossible de charger les profils nutritionnels.';
        this.notificationService.error('Profils nutritionnels inaccessibles.');
      },
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
      this.notificationService.warning('Formulation impossible : complétez les informations demandées.');
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
          if (reponse.succes) {
            this.notificationService.success('Formule créée avec succès.');
          } else {
            this.notificationService.warning(reponse.erreur ?? 'La formulation n’a pas pu être calculée.');
          }
        },
        error: (err) => {
          this.resultat = err.error ?? {
            succes: false,
            erreur: 'Erreur inattendue.',
            formule: null,
          };
          this.chargement = false;
          this.notificationService.error('Erreur lors de la création de la formule.');
        },
      });
  }
}
