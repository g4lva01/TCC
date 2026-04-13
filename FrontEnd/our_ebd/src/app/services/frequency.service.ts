import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FrequencyService {

  private baseUrl = 'http://localhost:8080/api/chamada/frequencia/trimestre';

  constructor(private http: HttpClient) { }

  getFrequenciasTodasTurmas(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/todas`);
  }

  getFrequenciaAluno(alunoId: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/aluno/${alunoId}`);
  }

  getTopPresencas(ano: number, trimestre: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/top-presencas?ano=${ano}&trimestre=${trimestre}`);
  }

  getTopFaltas(ano: number, trimestre: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/top-faltas?ano=${ano}&trimestre=${trimestre}`);
  }

  getFrequenciaPorTurma(ano: number, trimestre: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/turmas?ano=${ano}&trimestre=${trimestre}`);
  }

  getFaltasPorDia(ano: number, trimestre: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/faltas-por-dia?ano=${ano}&trimestre=${trimestre}`);
  }

  getDiasSemChamada(ano: number, trimestre: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/dias-sem-chamada?ano=${ano}&trimestre=${trimestre}`);
  }
}
