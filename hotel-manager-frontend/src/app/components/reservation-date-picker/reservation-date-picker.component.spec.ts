import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationDatePickerComponent } from './reservation-date-picker.component';

describe('ReservationDatePickerComponent', () => {
  let component: ReservationDatePickerComponent;
  let fixture: ComponentFixture<ReservationDatePickerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReservationDatePickerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReservationDatePickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
