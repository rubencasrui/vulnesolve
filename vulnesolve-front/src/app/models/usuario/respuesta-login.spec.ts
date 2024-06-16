import { RespuestaLogin } from './respuesta-login';

describe('RespuestaLogin', () => {
  it('should create an instance', () => {
    expect(new RespuestaLogin("", "", "", "", "")).toBeTruthy();
  });
});
