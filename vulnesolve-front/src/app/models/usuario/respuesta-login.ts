export class RespuestaLogin {
  mensaje: string;

  usuario: string;
  rol: string;
  token: string;

  error: string;

  constructor(mensaje: string, usuario: string, rol: string, token: string, error: string) {
    this.mensaje = mensaje;
    this.usuario = usuario;
    this.rol = rol;
    this.token = token;
    this.error = error;
  }

}
