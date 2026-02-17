import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from "@angular/common";

@Component({
  selector: 'app-menu-student',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './menu-student.component.html',
  styleUrls: ['./menu-student.component.css']
})
export class MenuStudentComponent implements OnInit {
  menuVisible = false;
  perfilMenuVisible = false;
  rolesUsuario: string[] = [];
  perfilAtivo: string | null = null;

  constructor(private router: Router) {}

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
      } catch (e) {
        console.error('Erro ao decodificar token:', e);
      }
    }
  }
}