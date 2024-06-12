import { PuertoNmap } from './puertoNmap';
import {JsonVulneSolve} from "../vulnerabilidades/json-vulne-solve";

describe('PuertoNmap', () => {
  it('should create an instance', () => {
    expect(
      new PuertoNmap(0,0, "", "",
        new JsonVulneSolve("", 0, "", 0, [])
      )
    ).toBeTruthy();
  });
});
