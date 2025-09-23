import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassReportComponent } from './class-report.component';

describe('ClassReportComponent', () => {
  let component: ClassReportComponent;
  let fixture: ComponentFixture<ClassReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClassReportComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClassReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
