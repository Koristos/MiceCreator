import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccomSearchComponent } from './accom-search.component';

describe('AccomSearchComponent', () => {
  let component: AccomSearchComponent;
  let fixture: ComponentFixture<AccomSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccomSearchComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccomSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
