import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewReportManagerComponent } from './view-report-manager.component';

describe('ViewReportManagerComponent', () => {
  let component: ViewReportManagerComponent;
  let fixture: ComponentFixture<ViewReportManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewReportManagerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewReportManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
