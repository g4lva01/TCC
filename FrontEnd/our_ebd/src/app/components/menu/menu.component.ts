import { Component, OnInit, inject } from '@angular/core';
import { RouterLink} from '@angular/router';
import { CommonModule } from "@angular/common";
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  menuVisible = false;
  perfilMenuVisible = false;
  rolesUsuario: string[] = [];
  perfilAtivo: string | null = null;

  private router = inject(Router);

  toggleMenu() {
    this.menuVisible = !this.menuVisible;
  }

  togglePerfilMenu() {
    this.perfilMenuVisible = !this.perfilMenuVisible;
  }

  trocarPerfil(perfil: string) {
    this.perfilAtivo = perfil;
    localStorage.setItem('perfilAtivo', perfil);
    this.perfilMenuVisible = false;
    
    if (this.perfilAtivo === 'ALUNO') {
      this.router.navigate(['/frequencyStudent']);
    } else {
      this.router.navigate(['/frequencyManager']);
    }
  }

  ngOnInit() {
    const token = localStorage.getItem('token');
    this.perfilAtivo = localStorage.getItem('perfilAtivo');

    if (token) {
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        this.rolesUsuario = (payload.roles || []).map((r: string) => r.replace('ROLE_', ''));
        console.log('Roles do usuário:', this.rolesUsuario);
        console.log('Perfil ativo:', this.perfilAtivo);
      } catch (e) {
        console.error('Erro ao decodificar token:', e);
      }
    }
  }
}
