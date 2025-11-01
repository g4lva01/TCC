import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GerenciarPerfisComponent } from './gerenciar-perfis.component';

describe('GerenciarPerfisComponent', () => {
  let component: GerenciarPerfisComponent;
  let fixture: ComponentFixture<GerenciarPerfisComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GerenciarPerfisComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GerenciarPerfisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
