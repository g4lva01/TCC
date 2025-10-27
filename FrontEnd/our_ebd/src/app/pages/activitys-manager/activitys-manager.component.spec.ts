import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivitysManagerComponent } from './activitys-manager.component';

describe('ActivitysManagerComponent', () => {
  let component: ActivitysManagerComponent;
  let fixture: ComponentFixture<ActivitysManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActivitysManagerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActivitysManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
