import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotelEventComponent } from './hotel-event.component';

describe('HotelEventComponent', () => {
  let component: HotelEventComponent;
  let fixture: ComponentFixture<HotelEventComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HotelEventComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HotelEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
