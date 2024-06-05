import { TestBed } from '@angular/core/testing';

import { VulnerabilidadesService } from './vulnerabilidades.service';

describe('VulnerabilidadesService', () => {
  let service: VulnerabilidadesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VulnerabilidadesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
