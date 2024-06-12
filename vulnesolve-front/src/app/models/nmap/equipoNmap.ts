import {PuertoNmap} from "./puertoNmap";

export class EquipoNmap {
  id      : string;
  x       : number;
  y       : number;
  ip      : string;
  mac     : string;
  tipo    : number;
  puertos : PuertoNmap[];

  constructor(
    id      : string,
    x       : number,
    y       : number,
    ip      : string,
    mac     : string,
    tipo    : number,
    puertos : PuertoNmap[]
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
