import { Component, OnInit } from '@angular/core';
import { MenuComponent } from '../../components/menu/menu.component';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-chamada',
  imports: [CommonModule, MenuComponent],
  templateUrl: './chamada.component.html',
  styleUrl: './chamada.component.css'
})

export class ChamadaComponent implements OnInit {
  turma: string = '';
  data: string = '';

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.turma = this.route.snapshot.paramMap.get('turma') || '';
    this.data = this.route.snapshot.paramMap.get('data') || '';
  }
}
