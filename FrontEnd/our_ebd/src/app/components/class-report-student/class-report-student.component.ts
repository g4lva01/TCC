import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-class-report-student',
  imports: [CommonModule],
  templateUrl: './class-report-student.component.html',
  styleUrl: './class-report-student.component.css'
})
export class ClassReportStudentComponent implements OnInit {
  relatorio: any[] = [];
  trimestres: string[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    const anoAtual = new Date().getFullYear();
    const anos = [anoAtual, anoAtual - 1];

    this.trimestres = anos.flatMap(ano => [
      `${ano}-T1`,
      `${ano}-T2`,
      `${ano}-T3`,
      `${ano}-T4`
    ]);
  }


  onTrimestreChange(event: any) {
    const valor = event.target.value;
    if(!valor) return;

    const [ano, trimestreStr] = valor.split('-T');
    const trimestre = parseInt(trimestreStr, 10);

    const alunoNome = localStorage.getItem('alunoNome');
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}`});

    this.http.get<any[]>(`http://localhost:8080/api/presencas/relatorio/aluno/${alunoNome}/${ano}/${trimestre}`, {headers})
      .subscribe({
        next: data => this.relatorio = data,
        error: err => console.error('Erro ao buscar relatório:', err)
      });
  }
}
