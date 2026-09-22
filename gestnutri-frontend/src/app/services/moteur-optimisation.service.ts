import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FormuleResponse } from './formule.model';

export interface MoteurOptimisationResquest {
  matieresPremieresIds: number[];
  profilNutritionnelId: number;
  quantiteTotale: number;
}

export interface MoteurOptimisationResponse {
  formule: FormuleResponse | null;
  succes: boolean;
  erreur: string | null;
}

@Injectable({ providedIn: 'root' })
export class MoteurOptimisationService {
  private readonly apiUrl = 'http://localhost:8080/api/moteur-optimisation';

  constructor(private http: HttpClient) {}

  resoudre(request: MoteurOptimisationResquest): Observable<MoteurOptimisationResponse> {
    return this.http.post<MoteurOptimisationResponse>(`${this.apiUrl}/resoudre`, request);
  }
}
