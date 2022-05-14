import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccomTypeComponent } from './accom-type.component';

describe('AccomTypeComponent', () => {
  let component: AccomTypeComponent;
  let fixture: ComponentFixture<AccomTypeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccomTypeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccomTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
