import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ChamadaService {
  private apiUrl = 'https://seu-backend.com/api/chamada'; // ✅ substitua pela sua URL real

  constructor(private http: HttpClient) {}

  registrarChamada(payload: any) {
    return this.http.post(this.apiUrl, payload);
  }

}
