import { Component, inject, PLATFORM_ID, Input, OnInit, SimpleChange, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule} from '@angular/common';
import { ChartOptions, ChartType, ChartData } from 'chart.js';
import { Chart, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, BarController } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';

Chart.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend, BarController);

@Component({
  selector: 'app-frequency-graph',
  imports: [CommonModule, BaseChartDirective],
  templateUrl: './frequency-graph.component.html',
  styleUrls: ['./frequency-graph.component.css']
})
export class FrequencyGraphComponent implements OnInit, OnChanges {
  @Input() titulo: string = '';
  @Input() datasets: any[] = [];
  @Input() labels: string[] = [];

  chartOptions: ChartOptions = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
        labels: { color: '#732991' }
      },
      title: {
        display: true,
        text: this.titulo,
        color: '#732991'
      }
    },
    scales: {
      x: { ticks: { color: '#732991' } },
      y: { ticks: { color: '#732991' } }
    }
  };

  chartType: ChartType = 'bar';
  chartData: ChartData<'bar'> = { labels: [], datasets: [] };

  get temDados(): boolean {
    return this.chartData.datasets?.some(ds => Array.isArray(ds.data) && ds.data.length > 0) ?? false;
  }

  ngOnInit(): void {
    this.atualizarGrafico();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['labels'] || changes['datasets'] || changes['titulo']) {
      this.atualizarGrafico();
    }
  }

  atualizarGrafico(): void {
    const safeDatasets = (this.datasets || []).map(ds => ({
      ...ds,
      data: (ds.data || []).map((v: any) => Number(v)) // força número
    }));

    this.chartData = {
      labels: this.labels || [],
      datasets: safeDatasets
    };

    if (this.chartOptions.plugins?.title) {
      (this.chartOptions.plugins.title as any).text = this.titulo;
    }
  }
}
