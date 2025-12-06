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

  constructor(private frequencyService: FrequencyService) {}

  ngOnInit(): void {
    const usuarioLogado = JSON.parse(localStorage.getItem('usuarioLogado') || '{}');
    const alunoId = usuarioLogado.id;

    this.frequencyService.getFrequenciaAluno(alunoId).subscribe({
      next: dado => {
        this.frequencias = [dado];
      },
      error: err => console.error('Erro ao buscar frequência do aluno:', err)
    });
  }
}
