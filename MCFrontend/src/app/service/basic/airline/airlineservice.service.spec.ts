import { TestBed } from '@angular/core/testing';

import { AirlineserviceService } from './airlineservice.service';

describe('AirlineserviceService', () => {
  let service: AirlineserviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AirlineserviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
