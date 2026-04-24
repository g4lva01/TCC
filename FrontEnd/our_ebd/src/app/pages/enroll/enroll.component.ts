import { Component, OnDestroy } from '@angular/core';
import { MenuComponent } from '../../components/menu/menu.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ZXingScannerModule } from '@zxing/ngx-scanner';
import { BarcodeFormat } from '@zxing/library';
import e from 'express';
import { log } from 'console';

@Component({
  selector: 'app-enroll',
  imports: [MenuComponent, CommonModule, FormsModule, ZXingScannerModule],
  templateUrl: './enroll.component.html',
  styleUrl: './enroll.component.css'
})
export class EnrollComponent implements OnDestroy {
  novoAluno = {
    nome: '',
    dtNascimento: '',
    matricula: ''
  };

  busca: string = '';
  alunoEncontrado: any = null;
  alunoNaoEncontrado: boolean = false;
  campoSelecionado: string = '';
  formats: BarcodeFormat[] = [BarcodeFormat.QR_CODE];
  mostrarScanner: boolean = false;
  availableDevices: MediaDeviceInfo[] = [];
  selectedDevice: MediaDeviceInfo | undefined;
  renderizarScanner: boolean = false;


  constructor(private http: HttpClient) {}

  matricularAluno() {
    this.http.post('http://localhost:8080/api/login/matricular', this.novoAluno)
      .subscribe({
        next: res => {
          this.alunoEncontrado = res;
          alert('Aluno matriculado com sucesso!');
        },
        error: err => {
          if (err.status === 400) {
            alert(err.error);
          } else {
             alert('Erro ao matricular aluno.');
          }
        }
      });
  }

  pesquisarAluno() {
    const token = localStorage.getItem('token');
    const headers = { Authorization: `Bearer ${token}` };

    this.http.get<any>(`http://localhost:8080/api/login/pesquisar?query=${this.busca}`, { headers })
      .subscribe({
        next: res => {
          this.alunoEncontrado = res;
          this.alunoNaoEncontrado = false;
        },
        error: err => {
          console.error('Erro ao pesquisar aluno:', err);
          this.alunoEncontrado = null;
          this.alunoNaoEncontrado = true;
        }
      });
  }

  desmatricularAluno(id: number) {
    this.http.delete(`http://localhost:8080/api/login/desmatricular/${id}`)
      .subscribe({
        next: () => {
          alert('Aluno desmatriculado com sucesso!');
          this.alunoEncontrado = null;
        },
        error: err => console.error('Erro ao desmatricular aluno:', err)
      });
  }

  onScanSuccess(valor: string) {
    console.log('Valor lido:', valor);

    if (this.campoSelecionado === 'busca') {
      this.busca = valor;
    } else if (this.campoSelecionado === 'matricula') {
      (this.novoAluno as any)[this.campoSelecionado] = valor;
    } else {
      this.novoAluno.matricula = valor;
    }

    this.toggleScanner();
  }

  async toggleScanner() {
    if(this.mostrarScanner) {
      this.mostrarScanner = false;

      this.renderizarScanner = false;
      this.selectedDevice = undefined;
    } else {
      this.selectedDevice = undefined;
      this.renderizarScanner = true;

      setTimeout(() => {
        this.mostrarScanner = true;
      }, 200);
    }
  }

  onCamerasFound(devices: MediaDeviceInfo[]) {
    this.availableDevices = devices;
    if (devices && devices.length > 0) {
      const backCamera = devices.find(d => d.label.toLowerCase().includes('back'));
      this.selectedDevice = backCamera || devices[0];
    }
  }

  onScanError(err: any) {
    console.error('Erro no scanner:', err);
    alert('Não foi possível acessar a câmera. Verifique permissão ou se o dispositivo está disponível.');
  }

  ngOnDestroy() {
    this.mostrarScanner = false;
    this.selectedDevice = undefined;
  }
}
