import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ProfilNutritionnelResponse {
  id: number;
  nomCategorie: string;
  stade: string;
  estPersonnalise: boolean;
  besoins: {
    id: number;
    nomNutriment: string;
    valeurMin: number;
    valeurMax: number;
  }[];
}

export interface ProfilNutritionnelResquest {
  nomCategorie: string;
  stade: string;
  estPersonnalise: boolean;
}

@Injectable({ providedIn: 'root' })
export class ProfilNutritionnelService {
  private readonly apiUrl = 'http://localhost:8082/api/profils-nutritionnels';

  constructor(private http: HttpClient) {}

  create(request: ProfilNutritionnelResquest): Observable<ProfilNutritionnelResponse> {
    return this.http.post<ProfilNutritionnelResponse>(this.apiUrl, request);
  }

  findAll(): Observable<ProfilNutritionnelResponse[]> {
    return this.http.get<ProfilNutritionnelResponse[]>(this.apiUrl);
  }
}
