import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MenuComponent } from '../../components/menu/menu.component';
import { ChamadaService } from '../../services/chamada.service';
import { NgxCurrencyDirective } from 'ngx-currency';

@Component({
  selector: 'app-chamada',
  standalone: true,
  imports: [CommonModule, MenuComponent, FormsModule, NgxCurrencyDirective],
  templateUrl: './chamada.component.html',
  styleUrl: './chamada.component.css'
})
export class ChamadaComponent implements OnInit {
  turmaNome: string = '';
  turmaId: number = 0;
  professorId: number = 1;
  data: string = '';
  visitantes: number = 0;
  oferta: number = 0;
  alunos: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private chamadaService: ChamadaService,
    private http: HttpClient
  ) {}

  ngOnInit() {
    this.turmaNome = this.route.snapshot.paramMap.get('turma') || '';
    this.data = this.route.snapshot.paramMap.get('data') || '';

    this.http.get<any>(`http://localhost:8080/api/turmas/nome/${this.turmaNome}`)
      .subscribe(turma => {
        this.turmaId = turma.id;

        this.http.get<any[]>(`http://localhost:8080/api/matriculas/turma/${this.turmaNome}/alunos`)
          .subscribe(alunos => {
            this.alunos = alunos.map(a => ({
              id: a.id,
              nome: a.nome,
              presente: false,
              biblia: false,
              revista: false
            }));
          });
      });
  }

  togglePresenca(index: number) {
    this.alunos[index].presente = !this.alunos[index].presente;
  }

  toggleBiblia(index: number) {
    if (!this.alunos[index].presente) {
      return;
    }
    this.alunos[index].biblia = !this.alunos[index].biblia;
  }

  finalizarChamada() {
    const presencas = this.alunos.map(aluno => ({
      alunoId: aluno.id,
      presente: aluno.presente,
      levouBiblia: aluno.biblia,
      levouRevista: aluno.revista
    }));

    const chamada = {
      turmaId: this.turmaId,
      dataChamada: this.data,
      statusChamada: 'Realizada',
      valorOferta: this.oferta,
      qtdVisitantes: this.visitantes,
      presencas: presencas
    };

    this.chamadaService.registrarChamada(chamada).subscribe({
      next: () => alert('Chamada registrada com sucesso!'),
      error: () => alert('Erro ao registrar chamada.')
    });
  }
}
