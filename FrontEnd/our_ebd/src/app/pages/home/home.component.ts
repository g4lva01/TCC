import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  imports: [RouterLink, CommonModule, ReactiveFormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  formulario = new FormGroup({
    matricula: new FormControl('', Validators.required),
    senha: new FormControl('', Validators.required)
  });

  constructor(private http: HttpClient, private router: Router) {}

  fazerLogin() {
    if (this.formulario.invalid) return;

    this.http.post<any>('http://localhost:8080/api/login', this.formulario.value)
      .subscribe({
        next: res => {
          localStorage.setItem('token', res.token); // ou res se for só string
          this.router.navigate(['/home']);
        },
        error: err => alert('Erro ao fazer login: ' + err.error)
      });
  }
}
