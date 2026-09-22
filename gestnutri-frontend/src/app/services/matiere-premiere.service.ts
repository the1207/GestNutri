import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface MatierePremiereResponse {
  id: number;
  nom: string;
  matiereSeche: number;
  celluloseBrute: number;
  matieresGrasses: number;
  energieMetabolisable: number;
  proteinesBrutes: number;
  lysine: number;
  methionine: number;
  aas: number;
  calcium: number;
  phosphore: number;
  sodium: number;
  prixUnitaire: number;
  tauxIncorporationMin: number;
  tauxIncorporationMax: number;
  disponible: boolean;
}

@Injectable({ providedIn: 'root' })
export class MatierePremiereService {
  private readonly apiUrl = 'http://localhost:8080/api/matieres-premieres';

  constructor(private http: HttpClient) {}

  findAll(): Observable<MatierePremiereResponse[]> {
    return this.http.get<MatierePremiereResponse[]>(this.apiUrl);
  }
}
