import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectionClassComponent } from './selection-class.component';

describe('SelectionClassComponent', () => {
  let component: SelectionClassComponent;
  let fixture: ComponentFixture<SelectionClassComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SelectionClassComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelectionClassComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
