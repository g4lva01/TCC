import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuStudentComponent } from '../../components/menu-student/menu-student.component';
import { FrequencyGraphStudentComponent } from "../../components/frequency-graph-student/frequency-graph-student.component";
import { FrequencyService } from '../../services/frequency.service';

@Component({
  selector: 'app-frequency-student',
  imports: [CommonModule, MenuStudentComponent, FrequencyGraphStudentComponent],
  templateUrl: './frequency-student.component.html',
  styleUrl: './frequency-student.component.css'
})
export class FrequencyStudentComponent implements OnInit {
  frequencias: any[] = [];
  hasPerfectAttendance = false;
  desempenhoAluno = 0;
  mediaTurma = 0;

  constructor(private frequencyService: FrequencyService) {}

  ngOnInit(): void {
    const alunoNome = localStorage.getItem('alunoNome') || '';

    this.frequencyService.getFrequenciaAluno(alunoNome).subscribe({
      next: dado => {
        this.frequencias = [dado];
        this.hasPerfectAttendance = dado.faltas === 0 && dado.presencas > 0;
        this.desempenhoAluno = dado.percentualPresenca || 0;
        this.mediaTurma = dado.mediaTurma || 0;
      },
      error: err => console.error('Erro ao buscar frequência do aluno:', err)
    });
  }
}
