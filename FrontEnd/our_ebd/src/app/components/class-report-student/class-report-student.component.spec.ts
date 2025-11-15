import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassReportStudentComponent } from './class-report-student.component';

describe('ClassReportStudentComponent', () => {
  let component: ClassReportStudentComponent;
  let fixture: ComponentFixture<ClassReportStudentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClassReportStudentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClassReportStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
