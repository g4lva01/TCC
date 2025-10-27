import { Component } from '@angular/core';
import { CommonModule } from "@angular/common";
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators, AbstractControl, ValidationErrors, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-new-password',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './new-password.component.html',
  styleUrl: './new-password.component.css'
})
export class NewPasswordComponent {
  formulario = new FormGroup({
    nomeMat: new FormControl('', Validators.required),
    nvSenha: new FormControl('', [Validators.required, this.senhaForteValidator]),
    cfNvSenha: new FormControl('', Validators.required)
  }, { validators: this.senhasIguaisValidator });

  constructor(private http: HttpClient, private router: Router) {}

  alterarLogin() {
    if (this.formulario.invalid) return;

    const body = {
      identificador: this.formulario.value.nomeMat,
      novaSenha: this.formulario.value.nvSenha,
      confirmarSenha: this.formulario.value.cfNvSenha
    };

    this.http.put('http://localhost:8080/api/login/alterar', body)
      .subscribe({
        next: res => {
          alert('Senha alterada com sucesso!');
          this.router.navigate(['/']);
        },
        error: err => alert('Erro ao alterar senha: ' + err.error)
      });
  }

  senhasIguaisValidator(control: AbstractControl): ValidationErrors | null {
    const senha = control.get('nvSenha')?.value;
    const cfSenha = control.get('cfNvSenha')?.value;
    return senha === cfSenha ? null : { senhasDiferentes: true };
  }

  senhaForteValidator(control: AbstractControl): ValidationErrors | null {
    const senha = control.value;
    const regex = /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return regex.test(senha) ? null : { senhaFraca: true };
  }
}