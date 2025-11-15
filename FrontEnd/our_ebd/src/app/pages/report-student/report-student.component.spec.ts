import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportStudentComponent } from './report-student.component';

describe('ReportStudentComponent', () => {
  let component: ReportStudentComponent;
  let fixture: ComponentFixture<ReportStudentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReportStudentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReportStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
