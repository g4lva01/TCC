import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { MenuComponent } from '../../components/menu/menu.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-gerenciar-perfis',
  imports: [CommonModule, MenuComponent, FormsModule],
  templateUrl: './gerenciar-perfis.component.html',
  styleUrl: './gerenciar-perfis.component.css'
})
export class GerenciarPerfisComponent implements OnInit {
  usuarios: any[] = [];
  perfisDisponiveis = ['ALUNO', 'PROFESSOR', 'GESTOR'];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });

    this.http.get<any[]>('http://localhost:8080/api/login/logins', { headers }).subscribe({
      next: res => {
        this.usuarios = res
        .filter(u => u.nome.toLowerCase() !== 'admin' && u.matricula !=='999999')
        .map(u => ({
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

  salvar(usuario: any) {
    const perfisSelecionados = Object.keys(usuario.perfisMap)
      .filter(p => usuario.perfisMap[p]);

    const perfilIds = perfisSelecionados.map((p: string) => this.mapearPerfilId(p));

    const dados = {
      pessoaId: usuario.id,
      perfilIds: perfilIds
    };

    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });

    this.http.put<any>('http://localhost:8080/api/login/perfil', dados, { headers }).subscribe({
      next: res => alert(res.message),
      error: err => console.error('Erro ao atualizar perfis:', err)
    });
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