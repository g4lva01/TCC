import { Component } from '@angular/core';
import { MenuComponent } from '../../components/menu/menu.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-enroll',
  imports: [MenuComponent, CommonModule, FormsModule],
  templateUrl: './enroll.component.html',
  styleUrl: './enroll.component.css'
})
export class EnrollComponent {
  novoAluno = {
    nome: '',
    dtNascimento: '',
    matricula: ''
  };

  busca: string = '';
  alunoEncontrado: any = null;
  alunoNaoEncontrado: boolean = false;

  constructor(private http: HttpClient) {}

  matricularAluno() {
    this.http.post('http://localhost:8080/api/login/matricular', this.novoAluno)
      .subscribe({
        next: res => {
          this.alunoEncontrado = res; // já pode mostrar o aluno cadastrado
          alert('Aluno matriculado com sucesso!');
        },
        error: err => console.error('Erro ao matricular aluno:', err)
      });
  }

  pesquisarAluno() {
    const token = localStorage.getItem('token');
    const headers = { Authorization: `Bearer ${token}` };

    this.http.get<any>(`http://localhost:8080/api/login/pesquisar?query=${this.busca}`, { headers })
      .subscribe({
        next: res => {
          this.alunoEncontrado = res;
          this.alunoNaoEncontrado = false;
        },
        error: err => {
          console.error('Erro ao pesquisar aluno:', err);
          this.alunoEncontrado = null;
          this.alunoNaoEncontrado = true;
        }
      });
  }

  desmatricularAluno(id: number) {
    this.http.delete(`http://localhost:8080/api/login/desmatricular/${id}`)
      .subscribe({
        next: () => {
          alert('Aluno desmatriculado com sucesso!');
          this.alunoEncontrado = null;
        },
        error: err => console.error('Erro ao desmatricular aluno:', err)
      });
  }
}
