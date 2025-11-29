import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { MenuComponent } from '../../components/menu/menu.component';
import { CommonModule } from '@angular/common';
import { ChamadaService } from '../../services/chamada.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

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
    revistas: number;
  }[] = [];

  constructor(
    private route: ActivatedRoute,
    private chamadaService: ChamadaService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.turma = this.route.snapshot.paramMap.get('turma') || '';

    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });

    this.http.get<any[]>(`http://localhost:8080/api/presencas/historico/turma/${this.turma}`, { headers })
      .subscribe({
        next: res => this.historico = res,
        error: err => console.error('Erro ao buscar histórico:', err)
      });
  }

  fazerChamada(data: string) {
    this.chamadaService.registrarChamada({ turma: this.turma, data }).subscribe({
      next: () => alert(`Chamada registrada para ${data}`),
      error: () => alert('Erro ao registrar chamada')
    });
  }
}
