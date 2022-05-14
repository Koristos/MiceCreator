import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegionEventSearchComponent } from './region-event-search.component';

describe('RegionEventSearchComponent', () => {
  let component: RegionEventSearchComponent;
  let fixture: ComponentFixture<RegionEventSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegionEventSearchComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegionEventSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
