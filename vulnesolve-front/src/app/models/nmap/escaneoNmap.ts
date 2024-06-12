import {EquipoNmap} from "./equipoNmap";
import {UnionNmap} from "./unionNmap";

export class EscaneoNmap {
  equipos : EquipoNmap[];
  uniones : UnionNmap[];

  constructor(
    equipos : EquipoNmap[],
    uniones : UnionNmap[]
  ) {
    this.equipos = equipos;
    this.uniones = uniones;
  }
}
