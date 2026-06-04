import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { environment } from '../../../environments/environment.prod';

@Component({
  selector: 'app-class-report-student',
  imports: [CommonModule, FormsModule],
  templateUrl: './class-report-student.component.html',
  styleUrl: './class-report-student.component.css'
})
export class ClassReportStudentComponent implements OnInit {
  relatorio: any[] = [];
  trimestres: string[] = [];
  trimestreSelecionado: string = '';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    const dataAtual = new Date();
    const anoAtual = dataAtual.getFullYear();
    const mesAtual = dataAtual.getMonth() + 1;

    const anos = [anoAtual, anoAtual - 1];
    this.trimestres = anos.flatMap(ano => [`${ano}-T1`, `${ano}-T2`, `${ano}-T3`, `${ano}-T4`]);

    const tAtual = Math.ceil(mesAtual / 3);
    this.trimestreSelecionado = `${anoAtual}-T${tAtual}`;

    this.buscarDados(this.trimestreSelecionado);
  }


  onTrimestreChange(event: any) {
    const valor = event.target.value;
    if (valor) {
      this.buscarDados(valor);
    }
  }

  buscarDados(valor: string) {
    const [ano, trimestreStr] = valor.split('-T');
    const trimestre = parseInt(trimestreStr, 10);

    const alunoNome = localStorage.getItem('alunoNome');
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}`});

    this.http.get<any[]>(`${environment.apiUrl}/api/presencas/relatorio/aluno/${alunoNome}/${ano}/${trimestre}`, {headers})
      .subscribe({
        next: data => this.relatorio = data,
        error: err => console.error('Erro ao buscar relatório:', err)
      });
  }
}
