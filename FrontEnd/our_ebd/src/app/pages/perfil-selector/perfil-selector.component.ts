import { CommonModule } from '@angular/common';
import { HttpClient} from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-perfil-selector',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './perfil-selector.component.html',
  styleUrl: './perfil-selector.component.css'
})
export class PerfilSelectorComponent {
  perfis: string[] = [];

  constructor(private router: Router, private http: HttpClient) {
    if (typeof window !== 'undefined') {
      const usuario = JSON.parse(localStorage.getItem('usuarioLogado') || '{}');

      if (usuario.token) {
        localStorage.setItem('token', usuario.token);
      }

      this.perfis = (usuario.roles || []).map((r: string) => r.replace('ROLE_', ''));
    }
  }

  acessarPerfil(perfil: string) {
    localStorage.setItem('perfilAtivo', perfil); // ✅ salva o perfil ativo

    switch (perfil) {
      case 'ALUNO':
        this.router.navigate(['/aluno']);
        break;
      case 'PROFESSOR':
        this.router.navigate(['/frequencyManager']);
        break;
      case 'GESTOR':
        this.router.navigate(['/frequencyManager']);
        break;
    }
  }
}
