import {Vulnerabilidad} from "./vulnerabilidad";

export class JsonVulneSolve {
  nombre : string;
  indiceVulneSolve : number;
  severidadVulneSolve : string;
  resultados : number;
  vulnerabilidades : Vulnerabilidad[];

  constructor(nombre : string, indiceVulneSolve : number, severidadVulneSolve : string, resultados : number, vulnerabilidades : Vulnerabilidad[]) {
    this.nombre = nombre;
    this.indiceVulneSolve = indiceVulneSolve;
    this.severidadVulneSolve = severidadVulneSolve;
    this.resultados = resultados;
    this.vulnerabilidades = vulnerabilidades;
  }
}
