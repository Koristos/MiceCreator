import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TourSearchComponent } from './tour-search.component';

describe('TourSearchComponent', () => {
  let component: TourSearchComponent;
  let fixture: ComponentFixture<TourSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TourSearchComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TourSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
