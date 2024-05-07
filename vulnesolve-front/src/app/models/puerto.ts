export class Puerto {
  numero      : number;
  estado      : number;
  nombre      : string;
  descripcion : string;

  constructor(
    numero : number,
    estado : number,
    nombre : string,
    descripcion : string
  ) {
    this.numero = numero;
    this.estado = estado;
    this.nombre = nombre;
    this.descripcion = descripcion;
  }
}
