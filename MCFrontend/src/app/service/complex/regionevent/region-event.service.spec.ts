import { TestBed } from '@angular/core/testing';

import { RegionEventService } from './region-event.service';

describe('RegionEventService', () => {
  let service: RegionEventService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RegionEventService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
