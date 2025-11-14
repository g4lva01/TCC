import { Component, OnInit } from '@angular/core';
import { RouterLink} from '@angular/router';
import { CommonModule } from "@angular/common";

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  menuVisible = false;
  rolesUsuario: string[] = [];
  perfilAtivo: string | null = null;

  toggleMenu() {
    this.menuVisible = !this.menuVisible;
  }

  ngOnInit() {
    const token = localStorage.getItem('token');
    this.perfilAtivo = localStorage.getItem('perfilAtivo'); // ✅ lê o perfil ativo

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
