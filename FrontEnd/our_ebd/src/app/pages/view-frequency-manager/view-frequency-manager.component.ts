import { Component, Input } from '@angular/core';
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
export class ViewFrequencyManagerComponent {
  frequencias: any[] = [];

  constructor(private frequencyService: FrequencyService) {}

  ngOnInit(): void {
    const turmaId = 1;
    this.frequencyService.getFrequenciasPorTurma(turmaId).subscribe({
      next: dados => this.frequencias = dados,
      error: err => console.error('Erro ao buscar frequências:', err)
    })
  }
}
