import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { MenuComponent } from '../../components/menu/menu.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-gerenciar-perfis',
  imports: [CommonModule, MenuComponent, FormsModule],
  templateUrl: './gerenciar-perfis.component.html',
  styleUrl: './gerenciar-perfis.component.css'
})
export class GerenciarPerfisComponent implements OnInit{
  usuarios: any[] = [];
  perfisDisponiveis = ['PROFESSOR', 'GESTOR'];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });

    this.http.get<any[]>('http://localhost:8080/api/login/logins', { headers }).subscribe({
      next: res => {
        // transforma perfis em mapa booleano
        this.usuarios = res.map(u => ({
          ...u,
          perfisMap: this.perfisDisponiveis.reduce((map: any, p) => {
            map[p] = u.perfis?.includes(p) || false;
            return map;
          }, {})
        }));
      },
      error: err => console.error('Erro ao buscar logins:', err)
    });
  }

  togglePerfil(usuario: any, perfil: string, checked: boolean) {
    if (!usuario.perfis) {
      usuario.perfis = [];
    }

    if (usuario.perfis.includes(perfil)) {
      usuario.perfis = usuario.perfis.filter((p: string) => p !== perfil);
    } else {
      usuario.perfis.push(perfil);
    }
  }

  salvar(usuario: any) {
    const perfisSelecionados = Object.keys(usuario.perfisMap)
      .filter(p => usuario.perfisMap[p]);

    const perfilIds = perfisSelecionados.map((p: string) => this.mapearPerfilId(p));

    this.http.put('http://localhost:8080/api/login/perfil', {
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
