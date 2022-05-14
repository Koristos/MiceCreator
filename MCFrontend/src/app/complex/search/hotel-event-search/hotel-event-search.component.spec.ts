import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotelEventSearchComponent } from './hotel-event-search.component';

describe('HotelEventSearchComponent', () => {
  let component: HotelEventSearchComponent;
  let fixture: ComponentFixture<HotelEventSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HotelEventSearchComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HotelEventSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
