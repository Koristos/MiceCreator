import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegionEventComponent } from './region-event.component';

describe('RegionEventComponent', () => {
  let component: RegionEventComponent;
  let fixture: ComponentFixture<RegionEventComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegionEventComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegionEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
