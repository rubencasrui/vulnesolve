import {Equipo} from "./equipo";
import {Union} from "./union";

export class Escaneo {
  equipos : Equipo[];
  uniones : Union[];

  constructor(
    equipos : Equipo[],
    uniones : Union[]
  ) {
    this.equipos = equipos;
    this.uniones = uniones;
  }
}
