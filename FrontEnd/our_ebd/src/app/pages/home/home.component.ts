import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router, RouterLink } from '@angular/router';
import { CommonModule} from '@angular/common';
import { Token } from '@angular/compiler';

@Component({
  selector: 'app-home',
  imports: [RouterLink, CommonModule, ReactiveFormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  formulario = new FormGroup({
    identificador: new FormControl('', Validators.required),
    senha: new FormControl('', Validators.required)
  });

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    if (typeof window !== 'undefined') {
      const usuarioJson = localStorage.getItem('usuarioLogado');
      if (usuarioJson) {
        try {
          const usuario = JSON.parse(usuarioJson);
          
          if (usuario && usuario.token && usuario.roles && usuario.roles.length > 0) {
            this.redirecionarPorPerfil(usuario.roles);
          }
        } catch (e) {
          localStorage.clear();
        }
      }
    }
  }

  fazerLogin() {
    if (this.formulario.invalid) return;

    this.http.post<any>('http://localhost:8080/api/login', this.formulario.value)
      .subscribe({
        next: res => {
          // Armazenamento centralizado
          localStorage.setItem('usuarioLogado', JSON.stringify(res));
          localStorage.setItem('token', res.token);
          localStorage.setItem('alunoNome', res.nome);

          this.redirecionarPorPerfil(res.roles || []);
        },
        error: err => {
          console.error(err);
          alert('Erro ao fazer login: Verifique suas credenciais.');
        }
      });
  }

  private redirecionarPorPerfil(roles: string[]) {
    let rota = '/home'; // Rota padrão caso nada coincida
    let perfilAtivo = '';

    if (roles.includes('ROLE_GESTOR')) {
      perfilAtivo = 'GESTOR';
      rota = '/frequencyManager';
    } else if (roles.includes('ROLE_PROFESSOR')) {
      perfilAtivo = 'PROFESSOR';
      rota = '/frequencyManager';
    } else if (roles.includes('ROLE_ALUNO')) {
      perfilAtivo = 'ALUNO';
      rota = '/frequencyStudent';
    }

    if (perfilAtivo) {
      localStorage.setItem('perfilAtivo', perfilAtivo);
      this.router.navigate([rota]);
    }
  }
}
