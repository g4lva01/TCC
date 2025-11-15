import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
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
    let alunoId: string | null = null;

    if (typeof window !== 'undefined') {
      alunoId = localStorage.getItem('alunoId');
    }

    if (!alunoId) {
      console.warn('ID do aluno não encontrado!');
      return;
    }

    this.http.get<any[]>(`/api/alunos/${alunoId}/relatorio`)
      .subscribe({
        next: data => this.relatorio = data,
        error: err => console.error('Erro ao buscar relatório:', err)
      });

    const anoAtual = new Date().getFullYear();
    const anos = [anoAtual, anoAtual -1];

    this.trimestres = anos.flatMap(ano => [
      `${ano}-T1`,
      `${ano}-T2`,
      `${ano}-T3`,
      `${ano}-T4`
    ]);
  }
}
