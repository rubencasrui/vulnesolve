import { TestBed } from '@angular/core/testing';

import { PuertosService } from './puertos.service';

describe('PuertosService', () => {
  let service: PuertosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PuertosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
