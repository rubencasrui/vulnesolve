import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { sinSesionGuard } from './sin-sesion.guard';

describe('sinSesionGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => sinSesionGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
