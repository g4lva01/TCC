import { Component } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { MenuComponent } from '../../components/menu/menu.component';

@Component({
  selector: 'app-gerenciar-perfis',
  imports: [CommonModule, MenuComponent],
  templateUrl: './gerenciar-perfis.component.html',
  styleUrl: './gerenciar-perfis.component.css'
})
export class GerenciarPerfisComponent {
  usuarios: any[] = [];
  perfisDisponiveis = ['PROFESSOR', 'GESTOR'];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    if (typeof window !== 'undefined' && window.localStorage) {
      const token = localStorage.getItem('token');

      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });
      
      this.http.get<any[]>('http://localhost:8080/api/login/logins', { headers }).subscribe({
        next: res => this.usuarios = res,
        error: err => console.error('Erro ao buscar logins:', err)
      });
    }
  }

  togglePerfil(usuario: any, perfil: string) {
    if (usuario.perfis.includes(perfil)) {
      usuario.perfis = usuario.perfis.filter((p: string) => p !== perfil);
    } else {
      usuario.perfis.push(perfil);
    }
  }

  salvar(usuario: any) {
    const perfilIds = usuario.perfis.map((p: string) => this.mapearPerfilId(p));
    this.http.put('/api/login/perfil', {
      pessoaId: usuario.id,
      perfilIds: perfilIds
    }).subscribe(() => alert('Perfis atualizados!'));
  }

  mapearPerfilId(perfil: string): number {
    switch (perfil) {
      case 'ALUNO': return 2;
      case 'PROFESSOR': return 3;
      case 'GESTOR': return 4;
      default: return 0;
    }
  }
}
