import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BaseChartDirective } from 'ng2-charts';
import { ChartOptions, ChartType, ChartData } from 'chart.js';

@Component({
  selector: 'app-frequency-graph',
  imports: [CommonModule, BaseChartDirective],
  templateUrl: './frequency-graph.component.html',
  styleUrls: ['./frequency-graph.component.css']
})
export class FrequencyGraphComponent {
  chartOptions: ChartOptions = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
        labels: {
          color: '#732991'
        }
      },
      title: {
        display: true,
        text: 'Frequência de Alunos por Turma',
        color: '#732991'
      }
    },
    scales: {
      x: {
        ticks: { color: '#732991' }
      },
      y: {
        ticks: { color: '#732991' }
      }
    }
  };

  chartType: ChartType = 'bar';

  chartData: ChartData<'bar'> = {
    labels: ['Aluno 1', 'Aluno 2', 'Aluno 3', 'Aluno 4'],
    datasets: [
      {
        label: 'Turma 1',
        data: [12, 18, 9, 14],
        backgroundColor: '#732991'
      },
      {
        label: 'Turma 2',
        data: [10, 20, 7, 16],
        backgroundColor: '#a55fc4'
      }
    ]
  };
  get temDados(): boolean {
    return this.chartData.datasets.some(dataset => dataset.data.length > 0);
  }
}