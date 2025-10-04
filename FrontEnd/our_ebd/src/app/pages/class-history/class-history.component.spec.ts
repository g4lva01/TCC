import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassHistoryComponent } from './class-history.component';

describe('ClassHistoryComponent', () => {
  let component: ClassHistoryComponent;
  let fixture: ComponentFixture<ClassHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClassHistoryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClassHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
