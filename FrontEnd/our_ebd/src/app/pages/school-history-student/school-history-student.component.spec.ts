import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SchoolHistoryStudentComponent } from './school-history-student.component';

describe('SchoolHistoryStudentComponent', () => {
  let component: SchoolHistoryStudentComponent;
  let fixture: ComponentFixture<SchoolHistoryStudentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SchoolHistoryStudentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SchoolHistoryStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
