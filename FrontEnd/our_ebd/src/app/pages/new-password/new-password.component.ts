import { Component } from '@angular/core';
import { CommonModule } from "@angular/common";
import { Router} from '@angular/router';
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
    nvSenha: new FormControl('', Validators.required),
    cfNvSenha: new FormControl('', Validators.required)
  }, { validators: this.senhasIguaisValidator });

  constructor(private router: Router) {}

  alterarLogin() {
    if (this.formulario.valid) {
      console.log('Formulário enviado:', this.formulario.value);
      this.router.navigate(['']); // redireciona para a tela principal
    } else {
      this.formulario.markAllAsTouched();
    }
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