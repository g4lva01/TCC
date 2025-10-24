import { TestBed } from '@angular/core/testing';

import { FrequencyServiceService } from './frequency.service';

describe('FrequencyServiceService', () => {
  let service: FrequencyServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FrequencyServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
