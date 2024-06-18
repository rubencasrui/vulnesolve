import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {PeticionCredenciales} from "../../models/usuario/peticion-credenciales";
import {RespuestaLogin} from "../../models/usuario/respuesta-login";
import {Usuario} from "../../models/usuario/usuario";

@Injectable({
  providedIn: 'root'
})
export class UsuariosService {

  urlBase : string = environment.urlBase;
  api : string = environment.api;

  sesionIniciada : boolean = false;
  esUsuarioAdministrador : boolean = false;

  constructor(
    private http: HttpClient
  ) { }

  actualizarValoresSesion(){
    let token = localStorage.getItem('token');
    if (token) {
      this.esTokenExpirado(token)
        .subscribe({
          next: (respuesta) => {
            this.sesionIniciada = !respuesta.expirado;
            if (respuesta.expirado) {
              localStorage.removeItem('token');
              this.sesionIniciada = false;
              this.esUsuarioAdministrador = false;
            }
            else {
              this.sesionIniciada = true;
              this.esAdministrador(token)
                .subscribe({
                  next: (respuesta) => {
                    this.esUsuarioAdministrador = respuesta.esAdmin;
                  },
                  error: (error) => {
                    console.log("Error esUsuarioAdministrador", error);
                    this.esUsuarioAdministrador = false;
                  }
                });
            }
          },
          error: (error) => {
            console.log("Error token expirado: ", error);
            localStorage.removeItem('token');
            this.sesionIniciada = false;
            this.esUsuarioAdministrador = false;
          }
        });
    }
    else {
      this.sesionIniciada = false;
      this.esUsuarioAdministrador = false;
    }
  }

  login(credenciales : PeticionCredenciales) {
    return this.http.post<RespuestaLogin>(this.urlBase + '/login', credenciales);
  }

  registro(credenciales : PeticionCredenciales) {
    return this.http.post<RespuestaLogin>(this.api + '/registrar', credenciales);
  }

  esTokenExpirado(token : string) {
    return this.http.get<{ expirado:boolean }>(this.api + '/usuario/token-expirado/'+token);
  }

  esAdministrador(token : string) {
    return this.http.get<{ esAdmin:boolean }>(this.api + '/usuario/es-administrador/'+token);
  }

  obtenerUsuariosNoAdmin() {
    return this.http.get<Usuario[]>(this.api + '/usuarios/no-admin');
  }

  hacerAdministrador(usuario : Usuario) {
    console.log(usuario)
    return this.http.put<Usuario>(this.api + '/usuario/hacer-admin/'+usuario.usuario, usuario);
  }

}
