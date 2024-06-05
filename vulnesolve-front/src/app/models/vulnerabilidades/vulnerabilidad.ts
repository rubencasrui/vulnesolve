import {Severidad} from "./severidad";

export class Vulnerabilidad {
  id : string;
  descripcion : string;
  severidades : Severidad[];

  constructor(id : string, descripcion : string, severidades : Severidad[]) {
    this.id = id;
    this.descripcion = descripcion;
    this.severidades = severidades;
  }
}
