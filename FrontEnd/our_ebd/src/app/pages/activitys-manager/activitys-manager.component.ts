import { Component, OnInit } from '@angular/core';
import { MenuComponent } from '../../components/menu/menu.component';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-activitys-manager',
  standalone: true,
  imports: [MenuComponent, CommonModule, FormsModule],
  templateUrl: './activitys-manager.component.html',
  styleUrl: './activitys-manager.component.css'
})
export class ActivitysManagerComponent implements OnInit{
  atividades: any[] = [];
  turmas: any[] = [];
  turmaSelecionada: string = '';
  temPermissao = false;
  mostrarModal = false;

  novaAtividade = {
    titulo: '',
    descricao: '',
    dataPublicacao: '',
    licao: ''
  };

  constructor(private http: HttpClient) {
    const usuarioLogado = JSON.parse(localStorage.getItem('usuarioLogado') || '{}');
    const roles: string[] = usuarioLogado.roles || [];

    this.temPermissao = roles.some(r => 
      ['ROLE_PROFESSOR', 'ROLE_GESTOR', 'ROLE_ADMIN'].includes(r)
    );

    console.log('Roles do usuário:', roles);
    console.log('Tem permissão:', this.temPermissao);
  }

  ngOnInit() {
    this.http.get<any[]>('http://localhost:8080/api/turmas')
      .subscribe(res => {
        this.turmas = res;
        if (res.length > 0) {
          this.turmaSelecionada = res[0].id;
          this.carregarAtividades();
        }
      });
  }

  carregarAtividades() {
    this.http.get<any[]>(`http://localhost:8080/api/atividade/turma/${this.turmaSelecionada}`)
      .subscribe(res => this.atividades = res);
  }

  salvarAtividade() {
    const body = {
      titulo: this.novaAtividade.titulo,
      descricao: this.novaAtividade.descricao,
      dataPublicacao: this.novaAtividade.dataPublicacao,
      licao: this.novaAtividade.licao,
      turma: { id: this.turmaSelecionada }
    };

    this.http.post('http://localhost:8080/api/atividade', body)
      .subscribe({
        next: () => {
          alert('Atividade criada com sucesso!');
          this.mostrarModal = false;
          this.carregarAtividades();
        },
        error: () => alert('Erro ao criar atividade')
      });
  }
}
