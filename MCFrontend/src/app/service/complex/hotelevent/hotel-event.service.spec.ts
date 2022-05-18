import { TestBed } from '@angular/core/testing';

import { HotelEventService } from './hotel-event.service';

describe('HotelEventService', () => {
  let service: HotelEventService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HotelEventService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
