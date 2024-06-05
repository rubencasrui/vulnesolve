export class Severidad {
  fuente: string;
  version: string;
  valor: number;
  severity: string;

  constructor(fuente: string, version: string, valor: number, severity: string) {
    this.fuente = fuente;
    this.version = version;
    this.valor = valor;
    this.severity = severity;
  }
}
