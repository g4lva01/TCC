import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MenuComponent } from '../../components/menu/menu.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-selection-class',
  standalone: true,
  imports: [MenuComponent, CommonModule],
  templateUrl: './selection-class.component.html',
  styleUrl: './selection-class.component.css'
})
export class SelectionClassComponent {
  turmas: string[] = ['Adultos', 'Jovens', 'Juniores', 'Crianças'];

  constructor(private router: Router) {}

  irParaChamada(turma: string) {
    const hoje = new Date().toISOString().split('T')[0]; // formato YYYY-MM-DD
    const url = `/chamada/${turma}/${hoje}`;
    this.router.navigateByUrl(url);
  }
}
