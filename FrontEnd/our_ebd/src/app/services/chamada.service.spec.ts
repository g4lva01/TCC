import { TestBed } from '@angular/core/testing';

import { ChamadaService } from './chamada.service';

describe('ChamadaService', () => {
  let service: ChamadaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChamadaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
