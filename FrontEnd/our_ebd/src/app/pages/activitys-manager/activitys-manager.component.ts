import { Component } from '@angular/core';
import { MenuComponent } from '../../components/menu/menu.component';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-activitys-manager',
  imports: [MenuComponent, CommonModule, FormsModule],
  templateUrl: './activitys-manager.component.html',
  styleUrl: './activitys-manager.component.css'
})
export class ActivitysManagerComponent {
  atividades: any[] = [];
  temPermissao = false;

  constructor(private http: HttpClient) {
    this.http.get<any[]>('http://localhost:8080/api/atividade')
      .subscribe(res => this.atividades = res);

    const usuarioLogado = JSON.parse(localStorage.getItem('usuarioLogado') || '{}');
    this.temPermissao = ['PROFESSOR', 'GESTOR', 'ADMIN'].includes(usuarioLogado.role);
  }

  mostrarModal = false;
  novaAtividade = {
    turmaId: '',
    titulo: '',
    descricao: '',
    dataPublicacao: '',
    licao: ''
  };
  turmas: any[] = [];

  ngOnInit() {
    this.http.get<any[]>('http://localhost:8080/api/turmas')
      .subscribe(res => this.turmas = res);
  }

  salvarAtividade() {
    const body = {
      titulo: this.novaAtividade.titulo,
      descricao: this.novaAtividade.descricao,
      dataPublicacao: this.novaAtividade.dataPublicacao,
      turma: { id: this.novaAtividade.turmaId }
    };

    this.http.post('http://localhost:8080/api/atividade', body)
      .subscribe({
        next: res => {
          alert('Atividade criada com sucesso!');
          this.mostrarModal = false;
          this.http.get<any[]>('http://localhost:8080/api/atividade')
            .subscribe(res => this.atividades = res);
        },
        error: err => alert('Erro ao criar atividade')
      }
    );
  }
}
