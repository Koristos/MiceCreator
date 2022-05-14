import { TestBed } from '@angular/core/testing';

import { BasicSearchService } from './basic-search.service';

describe('BasicSearchService', () => {
  let service: BasicSearchService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BasicSearchService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
