import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ChamadaService {
  private apiUrl = 'http://localhost:8080/api/chamada';

  constructor(private http: HttpClient) {}
  
  registrarChamada(payload: any) {
    return this.http.post(this.apiUrl, payload);
  }

}
