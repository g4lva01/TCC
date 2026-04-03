import { Component, OnInit } from '@angular/core';
import { MenuComponent } from '../../components/menu/menu.component';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MenuStudentComponent } from '../../components/menu-student/menu-student.component';
import { DomSanitizer } from '@angular/platform-browser';

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
  domingos: Date[] = [];
  trimestreSelecionado: number | null = null;
  
  novaAtividade = {
    titulo: '',
    descricao: '',
    numeroLicao: '',
    dataPublicacao: '',
    links: [] as {url: string, descricao: string}[],
    anexos: [] as {nomeArquivo: string; caminho: string; tipo: string; file?: File }[]
  };

  constructor(private http: HttpClient, public sanitizer: DomSanitizer) {
    const usuarioLogado = JSON.parse(localStorage.getItem('usuarioLogado') || '{}');
    const roles: string[] = usuarioLogado.roles || [];
    const perfilAtivo: string = localStorage.getItem('perfilAtivo') || '';

    this.temPermissao = ['PROFESSOR', 'GESTOR', 'ADMIN'].includes(perfilAtivo);

    console.log('Roles do usuário:', roles);
    console.log('Perfil ativo:', perfilAtivo);
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
      numeroLicao: atividade.numeroLicao,
      dataPublicacao: atividade.dataPublicacao,
      links: atividade.links,
      anexos: atividade.anexos
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
      dataPublicacao: this.novaAtividade.dataPublicacao,
      links: this.novaAtividade.links,
      anexos: this.novaAtividade.anexos,
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

  onTrimestreChange(event: any) {
    const trimestre = event.target.value;
    const ano = new  Date().getFullYear();

    this.http.get<string[]>(`http://localhost:8080/api/atividade/domingos/${ano}/${trimestre}`)
      .subscribe({
        next: data => {
          this.domingos = data.map(d => new Date(d + 'T00:00:00'));
        },
        error: err => console.error('Erro ao buscar domingos:', err)
      });
  }

  adicionarLink() {
    this.novaAtividade.links.push({url: '', descricao: ''});
  }

  removerLink(index: number) {
    this.novaAtividade.links.splice(index, 1);
  }
}
