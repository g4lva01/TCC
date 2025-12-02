import { Component, inject, PLATFORM_ID, Input, OnInit } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { BaseChartDirective } from 'ng2-charts';
import { ChartOptions, ChartType, ChartData } from 'chart.js';
import { Chart, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, BarController } from 'chart.js';
import { FrequencyService } from '../../services/frequency.service';

Chart.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, BarController);

@Component({
  selector: 'app-frequency-graph',
  imports: [CommonModule, BaseChartDirective],
  templateUrl: './frequency-graph.component.html',
  styleUrls: ['./frequency-graph.component.css']
})
export class FrequencyGraphComponent implements OnInit {
  isBrowser = isPlatformBrowser(inject(PLATFORM_ID));

  constructor(private frequencyService: FrequencyService) {}

  chartOptions: ChartOptions = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
        labels: { color: '#732991' }
      },
      title: {
        display: true,
        text: 'Frequência de Alunos por Turma',
        color: '#732991'
      }
    },
    scales: {
      x: { ticks: { color: '#732991' } },
      y: { ticks: { color: '#732991' } }
    }
  };

  chartType: ChartType = 'bar';

  chartData: ChartData<'bar'> = {
    labels: [],
    datasets: []
  };

  @Input() frequencias: any[] = [];

  get temDados(): boolean {
    return this.chartData.datasets.some(dataset => dataset.data.length > 0);
  }

  ngOnInit(): void {
    this.atualizarGrafico();
  }

  atualizarGrafico(): void {
    const labels = this.frequencias.map(f => f.nomeAluno);
    const data = this.frequencias.map(f => f.presencas);

    this.chartData = {
      labels,
      datasets: [{
        label: 'Presenças',
        data,
        backgroundColor: '#732991'
      }]
    };
  }
}
