import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewFrequencyManagerComponent } from './view-frequency-manager.component';

describe('ViewFrequencyManagerComponent', () => {
  let component: ViewFrequencyManagerComponent;
  let fixture: ComponentFixture<ViewFrequencyManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewFrequencyManagerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewFrequencyManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
