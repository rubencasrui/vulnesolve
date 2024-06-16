export class Usuario {

  id: number;
  usuario: string;
  esAdmin: boolean;

  constructor(id: number, usuario: string, esAdmin: boolean) {
    this.id = id;
    this.usuario = usuario;
    this.esAdmin = esAdmin;
  }

}
