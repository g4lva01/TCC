import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FrequencyService {

  constructor(private http: HttpClient) { }

  getFrequenciasTodasTurmas(): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8080/api/chamada/frequencia/trimestre/todas`);
  }

  getFrequenciaAluno(alunoId: number): Observable<any> {
    return this.http.get<any>(`http://localhost:8080/api/chamada/frequencia/trimestre/aluno/${alunoId}`);
  }
}
