import { Component, OnInit } from '@angular/core';
import { MenuStudentComponent } from '../../components/menu-student/menu-student.component';
import { CommonModule} from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-school-history-student',
  imports: [MenuStudentComponent, CommonModule, RouterLink, FormsModule],
  templateUrl: './school-history-student.component.html',
  styleUrl: './school-history-student.component.css'
})
export class SchoolHistoryStudentComponent implements OnInit {
  turmas: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    const alunoNome = localStorage.getItem('alunoNome');

    if (!alunoNome) {
      console.error('Nenhum aluno definido no localStorage');
      return;
    }

    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });

    if (alunoNome) {
      this.http.get<any[]>(`http://localhost:8080/api/presencas/historico/aluno/${alunoNome}`, { headers })
        .subscribe({
          next: res => {
            console.log('Resposta do back:', res);
            this.turmas = res;
          },
          error: err => console.error('Erro ao buscar histórico:', err)
        })
    }
  }

}
