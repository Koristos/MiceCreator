import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegionServComponent } from './region-serv.component';

describe('RegionServComponent', () => {
  let component: RegionServComponent;
  let fixture: ComponentFixture<RegionServComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegionServComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegionServComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
