import { Component, OnInit } from '@angular/core';
import { MenuComponent } from '../../components/menu/menu.component';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MenuStudentComponent } from '../../components/menu-student/menu-student.component';

@Component({
  selector: 'app-activitys-manager',
  standalone: true,
  imports: [MenuComponent, CommonModule, FormsModule, MenuStudentComponent],
  templateUrl: './activitys-manager.component.html',
  styleUrl: './activitys-manager.component.css'
})
export class ActivitysManagerComponent implements OnInit{
  atividades: any[] = [];
  turmas: any[] = [];
  turmaSelecionada: string = '';
  temPermissao = false;
  mostrarModal = false;
  atividadeEditandoId: number | null = null;

  novaAtividade = {
    titulo: '',
    descricao: '',
    numeroLicao: ''
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
  
  editarAtividade(atividade: any) {
    this.novaAtividade = {
      titulo: atividade.titulo,
      descricao: atividade.descricao,
      numeroLicao: atividade.numeroLicao
    };
    this.turmaSelecionada = atividade.turmaId;
    this.atividadeEditandoId = atividade.id;
    this.mostrarModal = true;
  }
  
  salvarAtividade() {
    const body = {
      titulo: this.novaAtividade.titulo,
      descricao: this.novaAtividade.descricao,
      numeroLicao: this.novaAtividade.numeroLicao,
      turma: { id: this.turmaSelecionada }
    };

    if (this.atividadeEditandoId) {
      this.http.put(`http://localhost:8080/api/atividade/${this.atividadeEditandoId}`, body)
      .subscribe({
        next: () => {
          alert('Atividade atualizada com sucesso!');
          this.mostrarModal = false;
          this.carregarAtividades();
        },
        error: () => alert('Erro ao atualizar atividade')
      });
    } else {
      this.http.post('http://localhost:8080/api/atividade', body)
        .subscribe({
          next: () => {
            alert('Atividade criada com sucesso!');
            this.mostrarModal = false;
            this.carregarAtividades();
          },
          error: (err) => {
            if (err.status === 409) {
              alert(err.error);
            } else {
            alert('Erro ao criar atividade');
          }
          }
        });
    }
  }

  excluirAtividade(atividade: any) {
    if (confirm('Tem certeza que deseja excluir esta atividade?')) {
      const body = {
        titulo: atividade.titulo,
        numeroLicao: atividade.numeroLicao,
        turma: { id: this.turmaSelecionada }
      };

      this.http.request('delete', 'http://localhost:8080/api/atividade', {body})
        .subscribe({
          next: () => {
            alert('Atividade excluída com sucesso!');
            this.carregarAtividades();
          },
          error: () => alert('Erro ao excluir atividade')
        });
    }
  }
}
