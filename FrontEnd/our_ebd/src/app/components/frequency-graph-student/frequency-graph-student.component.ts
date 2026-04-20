import { Component, inject, PLATFORM_ID, Input, OnInit, SimpleChanges, OnChanges } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { BaseChartDirective } from 'ng2-charts';
import { ChartOptions, ChartType, ChartData } from 'chart.js';
import { Chart, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, BarController } from 'chart.js';
import { FrequencyService } from '../../services/frequency.service';

Chart.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, BarController);

@Component({
  selector: 'app-frequency-graph-student',
  imports: [CommonModule, BaseChartDirective],
  templateUrl: './frequency-graph-student.component.html',
  styleUrl: './frequency-graph-student.component.css'
})
export class FrequencyGraphStudentComponent implements OnInit, OnChanges{
  isBrowser = isPlatformBrowser(inject(PLATFORM_ID));

  constructor(private frequencyService: FrequencyService) {}

  chartOptions: ChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: { display: false },
      title: { display: false }
    },
    scales: {
      x: { 
        grid: { display: false },
        ticks: { color: '#999' }
      },
      y: { 
        beginAtZero: true,
        grid: { color: '#f0f0f0' },
        ticks: { color: '#999' }
      }
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

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['frequencias'] && this.frequencias && this.frequencias.length >0) {
      this.atualizarGrafico();
    }
  }

  ngOnInit(): void {
    this.atualizarGrafico();
  }

  atualizarGrafico(): void {
    if (!this.frequencias || this.frequencias.length === 0) return;

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
