import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-class-report',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './class-report.component.html',
  styleUrls: ['./class-report.component.css']
})
export class ClassReportComponent {
  grupos = [
    { nome: 'Adultos', matriculados: 0, presentes: 0, ausentes: 0, biblia: 0, revistas: 0, oferta: 0 },
    { nome: 'Jovens', matriculados: 0, presentes: 0, ausentes: 0, biblia: 0, revistas: 0, oferta: 0 },
    { nome: 'Juniores', matriculados: 0, presentes: 0, ausentes: 0, biblia: 0, revistas: 0, oferta: 0 },
    { nome: 'Crianças', matriculados: 0, presentes: 0, ausentes: 0, biblia: 0, revistas: 0, oferta: 0 }
  ];

  totalGeral = { matriculados: 0, presentes: 0, ausentes: 0, biblia: 0, revistas: 0, oferta: 0 };

  trimestres: string[] = [];

  ngOnInit() {
    const anoAtual = new Date().getFullYear();
    const anos = [anoAtual, anoAtual -1];

    this.trimestres = anos.flatMap(ano => [
      `${ano}-T1`,
      `${ano}-T2`,
      `${ano}-T3`,
      `${ano}-T4`
    ]);
  }

  verMais(grupo: string) {
    console.log(`Ver mais sobre ${grupo}`);
  }
}
