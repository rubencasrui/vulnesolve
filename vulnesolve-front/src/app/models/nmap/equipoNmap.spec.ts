import { EquipoNmap } from './equipoNmap';

describe('EquipoNmap', () => {
  it('should create an instance', () => {
    expect(new EquipoNmap("", 0, 0, "", "", 0, [])).toBeTruthy();
  });
});
