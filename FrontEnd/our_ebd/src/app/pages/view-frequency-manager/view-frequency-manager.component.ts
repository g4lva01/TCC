import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuComponent } from "../../components/menu/menu.component";
import { FrequencyGraphComponent } from "../../components/frequency-graph/frequency-graph.component";
import { FrequencyService } from '../../services/frequency.service';

@Component({
  selector: 'app-view-frequency-manager',
  standalone: true,
  imports: [CommonModule, MenuComponent, FrequencyGraphComponent],
  templateUrl: './view-frequency-manager.component.html',
  styleUrl: './view-frequency-manager.component.css'
})
export class ViewFrequencyManagerComponent implements OnInit {
  frequencias: any[] = [];
  turmas: string[] = [];
  agrupadoPorTurma: Record<string, any[]> = {};

  constructor(private frequencyService: FrequencyService) {}

  ngOnInit(): void {
    this.frequencyService.getFrequenciasTodasTurmas().subscribe({
      next: dados => {
        this.turmas = [...new Set(dados.map(d => d.nomeTurma))];
        this.agrupadoPorTurma = dados.reduce((acc, item) => {
          if (!acc[item.nomeTurma]) acc[item.nomeTurma] = [];
          acc[item.nomeTurma].push(item);
          return acc;
        }, {});
      },
      error: err => console.error('Erro ao buscar frequências:', err)
    });
  }
}
