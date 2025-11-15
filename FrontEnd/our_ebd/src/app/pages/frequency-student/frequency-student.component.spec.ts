import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FrequencyStudentComponent } from './frequency-student.component';

describe('FrequencyStudentComponent', () => {
  let component: FrequencyStudentComponent;
  let fixture: ComponentFixture<FrequencyStudentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FrequencyStudentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FrequencyStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
