import { TestBed } from '@angular/core/testing';

import { AccomTypeService } from './accom-type.service';

describe('AccomTypeService', () => {
  let service: AccomTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccomTypeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
