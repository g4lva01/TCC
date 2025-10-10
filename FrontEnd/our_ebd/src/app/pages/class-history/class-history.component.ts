import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { MenuComponent } from '../../components/menu/menu.component';
import { CommonModule } from '@angular/common';
import { ChamadaService } from '../../services/chamada.service';

@Component({
  selector: 'app-class-history',
  imports: [CommonModule, MenuComponent, RouterLink],
  standalone: true,
  templateUrl: './class-history.component.html',
  styleUrls: ['./class-history.component.css']
})
export class ClassHistoryComponent implements OnInit {
  turma: string = '';

  historico: {
    data: string;
    presente: number;
    levouBiblia: number;
    Revistas: number;
  }[] = [];

  constructor(
    private route: ActivatedRoute,
    private chamadaService: ChamadaService
  ) {}

  ngOnInit(): void {
    this.turma = this.route.snapshot.paramMap.get('turma') || '';

    // Simulação de dados — substitua por chamada real ao backend
    this.historico = [
      { data: '04/05/2025', presente: 12, levouBiblia: 10, Revistas: 8 },
      { data: '11/05/2025', presente: 9, levouBiblia: 7, Revistas: 6 },
      { data: '18/05/2025', presente: 11, levouBiblia: 9, Revistas: 7 },
      { data: '25/05/2025', presente: 13, levouBiblia: 12, Revistas: 10 }
    ];
  }

  fazerChamada(data: string) {
    this.chamadaService.registrarChamada({ turma: this.turma, data }).subscribe({
      next: () => alert(`Chamada registrada para ${data}`),
      error: () => alert('Erro ao registrar chamada')
    });
  }
}
