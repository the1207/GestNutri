import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FormuleResponse } from './formule.model';

@Injectable({ providedIn: 'root' })
export class FormuleService {
  private readonly apiUrl = 'http://localhost:8082/api/formules';

  constructor(private http: HttpClient) {}

  findAll(): Observable<FormuleResponse[]> {
    return this.http.get<FormuleResponse[]>(this.apiUrl);
  }

  findById(id: number): Observable<FormuleResponse> {
    return this.http.get<FormuleResponse>(`${this.apiUrl}/${id}`);
  }

  telechargerPdf(id: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${id}/export/pdf`, { responseType: 'blob' });
  }

  telechargerCsv(id: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${id}/export/csv`, { responseType: 'blob' });
  }
}