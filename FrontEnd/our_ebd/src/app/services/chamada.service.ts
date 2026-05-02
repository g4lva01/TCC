import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ChamadaService {
  private apiUrl = 'http://localhost:8080/api/chamada';

  constructor(private http: HttpClient) {}

  private getHeaders() {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  atualizarChamada(turmaNome: string, data: string, chamada: any) {
    const headers = this.getHeaders();
    return this.http.put(
      `http://localhost:8080/api/chamada/${turmaNome}/${data}`, 
      chamada, 
      { headers }
    );
  }
  
  registrarChamada(payload: any) {
    const headers = this.getHeaders();
    return this.http.post(this.apiUrl, payload, { headers });
  }
}
