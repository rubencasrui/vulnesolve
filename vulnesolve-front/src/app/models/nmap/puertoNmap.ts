import {JsonVulneSolve} from "../vulnerabilidades/json-vulne-solve";

export class PuertoNmap {
  numero           : number;
  estado           : number;
  nombre           : string;
  descripcion      : string;
  vulnerabilidades : JsonVulneSolve;

  constructor(numero : number, estado : number, nombre : string, descripcion : string, vulnerabilidades : JsonVulneSolve) {
    this.numero = numero;
    this.estado = estado;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.vulnerabilidades = vulnerabilidades;
  }
}
