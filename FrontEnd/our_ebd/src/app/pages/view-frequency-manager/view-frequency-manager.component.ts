import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuComponent } from "../../components/menu/menu.component";
import { FrequencyGraphComponent } from "../../components/frequency-graph/frequency-graph.component";
import { FrequencyService } from '../../services/frequency.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-view-frequency-manager',
  standalone: true,
  imports: [CommonModule, MenuComponent, FrequencyGraphComponent, RouterLink],
  templateUrl: './view-frequency-manager.component.html',
  styleUrl: './view-frequency-manager.component.css'
})
export class ViewFrequencyManagerComponent implements OnInit {
  topPresencas: any[] = [];
  topFaltas: any[] = [];
  frequenciaPorTurma: any[] = [];
  faltasPorDia: any[] = [];
  diasSemChamada: AvisoSemChamada[] = [];
  turmasLabels: string[] = [];
  turmasData: number[] = [];

  presencasLabels: string[] = [];
  presencasData: number[] = [];
  faltasLabels: string[] = [];
  faltasData: number[] = [];
  faltasDiaLabels: string[] = [];
  faltasDiaData: number[] = [];

  constructor(private frequencyService: FrequencyService) {}

  ngOnInit(): void {
    const hoje = new Date();
    const ano = hoje.getFullYear();
    const trimestre = Math.floor(hoje.getMonth() / 3) + 1;
    
    this.frequencyService.getTopPresencas(ano, trimestre).subscribe(res => {
      this.topPresencas = res;
      this.presencasLabels = res.map(a => a.nomeAluno);
      this.presencasData = res.map(a => Number(a.presencas));
    });

    this.frequencyService.getTopFaltas(ano, trimestre).subscribe(res => {
      this.topFaltas = res;
      this.faltasLabels = res.map(a => a.nomeAluno);
      this.faltasData = res.map(a => Number(a.faltas));
    });

    this.frequencyService.getFaltasPorDia(ano, trimestre).subscribe(res => {
      this.faltasPorDia = res;
      this.faltasDiaLabels = res.map(d => d.data);
      this.faltasDiaData = res.map(d => Number(d.totalFaltas));
    });

    this.frequencyService.getDiasSemChamada(ano,  trimestre)
    .subscribe( res => this.diasSemChamada = res);

    this.frequencyService.getFrequenciaPorTurma(ano, trimestre).subscribe(res => {
      this.turmasLabels = res.map(t => t.nomeTurma);
      this.turmasData = res.map(t => Number(t.frequencia));
    });
  }
}

interface AvisoSemChamada {
  nomeTurma: string;
  diasSemChamada: string[];
}