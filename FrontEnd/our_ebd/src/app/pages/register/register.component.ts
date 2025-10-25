import { Component } from '@angular/core';
import { CommonModule } from "@angular/common";
import { Router} from '@angular/router';
import { FormGroup, FormControl, Validators, AbstractControl, ValidationErrors, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  formulario = new FormGroup({
    nome: new FormControl('', Validators.required),
    dtNascimento: new FormControl('', Validators.required),
    matricula: new FormControl('', Validators.required),
    senha: new FormControl('', Validators.required),
    cfSenha: new FormControl('', Validators.required)
  }, { validators: this.senhasIguaisValidator });

  constructor(private router: Router, private http: HttpClient) {}

  criarLogin() {
    this.http.post('http://localhost:8080/api/login/criar', this.formulario.value, {
      withCredentials: true
    })
    .subscribe({
      next: res => {
        alert('Login criado com sucesso!');
        this.router.navigate(['/']);
      },
      error: err => alert('Erro: ' + err.error)
    });
  }

  senhasIguaisValidator(control: AbstractControl): ValidationErrors | null {
    const senha = control.get('senha')?.value;
    const cfSenha = control.get('cfSenha')?.value;
    return senha === cfSenha ? null : { senhasDiferentes: true };
  }
}

function senhaForteValidator(control: AbstractControl): ValidationErrors | null {
  const senha = control.value;
  const regex = /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

  return regex.test(senha) ? null : { senhaFraca: true };
}