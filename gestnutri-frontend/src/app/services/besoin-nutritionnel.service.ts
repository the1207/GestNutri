import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface BesoinNutritionnelResquest {
  nomNutriment: string;
  valeurMin: number;
  valeurMax: number;
  profilNutritionnelId: number;
}

@Injectable({ providedIn: 'root' })
export class BesoinNutritionnelService {
  private readonly apiUrl = 'http://localhost:8082/api/besoins-nutritionnels';

  constructor(private http: HttpClient) {}

  create(request: BesoinNutritionnelResquest): Observable<unknown> {
    return this.http.post(this.apiUrl, request);
  }
}