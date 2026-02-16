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
  domingosTrimestre: string[] = [];
  trimestres: string[] = [];
  trimestreSelecionado: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private chamadaService: ChamadaService,
    private http: HttpClient
  ) {}

  ngOnInit() {
    const anoAtual = new Date().getFullYear();
    const anos = [anoAtual, anoAtual - 1];

    this.trimestres = anos.flatMap(ano => [
      `${ano}-T1`,
      `${ano}-T2`,
      `${ano}-T3`,
      `${ano}-T4`
    ]);

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

  onTrimestreChange() {
    if (!this.trimestreSelecionado) return;

    const [anoStr, trimestre] = this.trimestreSelecionado.split('-T');
    const ano = parseInt(anoStr, 10);

    let startMonth = 0;
    if (trimestre === '1') startMonth = 0;
    if (trimestre === '2') startMonth = 3;
    if (trimestre === '3') startMonth = 6;
    if (trimestre === '4') startMonth = 9;

    const startDate = new Date(ano, startMonth, 1);
    const endDate = new Date(ano, startMonth + 3, 0);

    this.gerarDomingosTrimestre(startDate, endDate);
  }

  selecionarDomingo(d: string) {
    this.data = d;
    this.carregarChamada();
  }

  gerarDomingosTrimestre(dataMin: Date, dataMax: Date) {
    const start = new Date(dataMin);
    const end = new Date(dataMax);
    const domingos: string[] = [];

    let current = new Date(start);
    while (current <= end) {
      if (current.getDay() === 0) {
        domingos.push(current.toISOString().split('T')[0]);
      }
      current.setDate(current.getDate() + 1);
    }

    this.domingosTrimestre = domingos;
  }
  
  carregarChamada() {
    if (!this.data) return;

    this.http.get<any>(`http://localhost:8080/api/chamada/${this.turmaNome}/${this.data}`)
      .subscribe({
        next: chamada => {
          this.visitantes = chamada.qtdVisitantes;
          this.oferta = chamada.valorOferta;
          this.alunos = chamada.presencas.map((p: any) => ({
            id: p.alunoId,
            nome: p.nome,
            presente: p.presente,
            biblia: p.levouBiblia,
            revista: p.levouRevista
          }));
        },
        error: err => {
          if (err.status === 404) {
            this.visitantes = 0;
            this.oferta = 0;
            this.alunos.forEach(a => {
              a.presente = false;
              a.biblia = false;
              a.revista = false;
            });
          } else {
            console.error('Erro ao carregar chamada:', err);
          }
        }
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
