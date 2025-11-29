import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-class-report',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './class-report.component.html',
  styleUrls: ['./class-report.component.css']
})
export class ClassReportComponent {
  grupos: any[] = [];
  totalGeral = { matriculados: 0, presentes: 0, ausentes: 0, biblia: 0, revistas: 0, oferta: 0 };

  constructor(private http: HttpClient) {}

  trimestres: string[] = [];
  trimestreSelecionado: string | null = null;
  dataSelecionada: string | null = null;
  dataMin: string | null = null;
  dataMax: string | null = null;

  ngOnInit() {
    const anoAtual = new Date().getFullYear();
    const anos = [anoAtual, anoAtual - 1];

    this.trimestres = anos.flatMap(ano => [
      `${ano}-T1`,
      `${ano}-T2`,
      `${ano}-T3`,
      `${ano}-T4`
    ]);
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

    this.dataMin = startDate.toISOString().split('T')[0];
    this.dataMax = endDate.toISOString().split('T')[0];
    this.dataSelecionada = null;
  }

  onDataChange() {
    if (!this.dataSelecionada) return;

    this.http.get<any[]>(`http://localhost:8080/api/presencas/historico/data/${this.dataSelecionada}`)
      .subscribe(dados => {
        this.grupos = dados;
        this.calcularTotal();
      }, error => {
        console.error('Erro ao buscar dados:', error);
        this.grupos = [];
      });
  }

  calcularTotal() {
    this.totalGeral = this.grupos.reduce((acc, g) => ({
      matriculados: acc.matriculados + g.matriculados,
      presentes: acc.presentes + g.presentes,
      ausentes: acc.ausentes + g.ausentes,
      biblia: acc.biblia + g.biblia,
      revistas: acc.revistas + g.revistas,
      oferta: acc.oferta + g.oferta
    }), { matriculados: 0, presentes: 0, ausentes: 0, biblia: 0, revistas: 0, oferta: 0 });
  }
  verMais(grupo: string) {
    console.log(`Ver mais sobre ${grupo}`);
  }
}
