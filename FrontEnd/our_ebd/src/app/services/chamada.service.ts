import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ChamadaService {
  private apiUrl = 'http://localhost:8080/api/chamada';

  constructor(private http: HttpClient) {}

  atualizarChamada(turmaNome: string, data: string, chamada: any) {
    return this.http.put(`http://localhost:8080/api/chamada/${turmaNome}/${data}`, chamada);
  }
  
  registrarChamada(payload: any) {
    return this.http.post(this.apiUrl, payload);
  }

}
