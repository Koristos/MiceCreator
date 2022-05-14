import { TestBed } from '@angular/core/testing';

import { HotelServService } from './hotel-serv.service';

describe('HotelServService', () => {
  let service: HotelServService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HotelServService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
