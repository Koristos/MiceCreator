import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotelServComponent } from './hotel-serv.component';

describe('HotelServComponent', () => {
  let component: HotelServComponent;
  let fixture: ComponentFixture<HotelServComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HotelServComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HotelServComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
