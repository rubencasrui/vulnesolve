import {Puerto} from "./puerto";

export class Equipo {
  id      : string;
  x       : number;
  y       : number;
  ip      : string;
  mac     : string;
  tipo    : number;
  puertos : Puerto[];

  constructor(
    id      : string,
    x       : number,
    y       : number,
    ip      : string,
    mac     : string,
    tipo    : number,
    puertos : Puerto[]
  ) {
    this.id      = id;
    this.x       = x;
    this.y       = y;
    this.ip      = ip;
    this.mac     = mac;
    this.tipo    = tipo;
    this.puertos = puertos;
  }
}
