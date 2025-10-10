import { Component, OnInit } from '@angular/core';
import { MenuComponent } from '../../components/menu/menu.component';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ChamadaService } from '../../services/chamada.service';

@Component({
  selector: 'app-chamada',
  imports: [CommonModule, MenuComponent, FormsModule],
  templateUrl: './chamada.component.html',
  styleUrl: './chamada.component.css'
})

export class ChamadaComponent implements OnInit {
  turma: string = '';
  data: string = '';
  visitantes: number = 0;
  oferta: string = '0,00';

  constructor(private route: ActivatedRoute, private chamadaService: ChamadaService) {}

  ngOnInit() {
    this.turma = this.route.snapshot.paramMap.get('turma') || '';
    this.data = this.route.snapshot.paramMap.get('data') || '';
  }

  alunos = [
    { nome: 'João', presente: false, biblia: false },
    { nome: 'Maria', presente: false, biblia: false },
    // ...
  ];

  togglePresenca(index: number) {
    this.alunos[index].presente = !this.alunos[index].presente;
  }

  toggleBiblia(index: number) {
    this.alunos[index].biblia = !this.alunos[index].biblia;
  }

  finalizarChamada() {
    const chamada = {
      turma: this.turma,
      data: this.data,
      alunos: this.alunos,
      visitantes: this.visitantes,
      oferta: this.oferta
    };

    this.chamadaService.registrarChamada(chamada).subscribe({
      next: () => alert('Chamada registrada com sucesso!'),
      error: () => alert('Erro ao registrar chamada.')
    });
  }
}
