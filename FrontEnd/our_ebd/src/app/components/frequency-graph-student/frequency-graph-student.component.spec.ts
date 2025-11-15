import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FrequencyGraphStudentComponent } from './frequency-graph-student.component';

describe('FrequencyGraphStudentComponent', () => {
  let component: FrequencyGraphStudentComponent;
  let fixture: ComponentFixture<FrequencyGraphStudentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FrequencyGraphStudentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FrequencyGraphStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
