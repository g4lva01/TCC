import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChamadaComponent } from './chamada.component';

describe('ChamadaComponent', () => {
  let component: ChamadaComponent;
  let fixture: ComponentFixture<ChamadaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChamadaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChamadaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
