import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment.prod';

@Injectable({ providedIn: 'root' })
export class ChamadaService {
  private apiUrl = `${environment.apiUrl}/api/chamada`;

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
      `${environment.apiUrl}/api/chamada/${turmaNome}/${data}`, 
      chamada, 
      { headers }
    );
  }
  
  registrarChamada(payload: any) {
    const headers = this.getHeaders();
    return this.http.post(this.apiUrl, payload, { headers });
  }
}
