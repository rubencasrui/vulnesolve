import { PeticionCredenciales } from './peticion-credenciales';

describe('PeticionCredenciales', () => {
  it('should create an instance', () => {
    expect(new PeticionCredenciales("", "")).toBeTruthy();
  });
});
