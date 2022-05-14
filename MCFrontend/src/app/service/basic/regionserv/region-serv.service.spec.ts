import { TestBed } from '@angular/core/testing';

import { RegionServService } from './region-serv.service';

describe('RegionServService', () => {
  let service: RegionServService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RegionServService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
