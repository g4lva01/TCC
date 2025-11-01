import { Component } from '@angular/core';
import {HttpClient} from '@angular/common/http';
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
  perfisDisponiveis = ['ALUNO', 'PROFESSOR', 'GESTOR'];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http.get<any[]>('/api/login/logins').subscribe(res => {
      this.usuarios = res;
    });
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
