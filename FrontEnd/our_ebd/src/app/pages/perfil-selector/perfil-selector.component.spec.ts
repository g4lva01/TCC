import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PerfilSelectorComponent } from './perfil-selector.component';

describe('PerfilSelectorComponent', () => {
  let component: PerfilSelectorComponent;
  let fixture: ComponentFixture<PerfilSelectorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PerfilSelectorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PerfilSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
