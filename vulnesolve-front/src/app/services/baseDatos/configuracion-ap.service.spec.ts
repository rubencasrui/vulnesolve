import { TestBed } from '@angular/core/testing';

import { ConfiguracionApiService } from './configuracion-api.service';

describe('ConfiguracionApiService', () => {
  let service: ConfiguracionApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConfiguracionApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
