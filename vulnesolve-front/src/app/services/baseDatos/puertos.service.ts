import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Puerto} from "../../models/dto/puerto";

@Injectable({
  providedIn: 'root'
})
export class PuertosService {

  api : string = environment.api;

  constructor(
    private http: HttpClient
  ) { }

  crearPuerto(puerto : Puerto) {
    return this.http.post<Puerto>(this.api + '/puerto', puerto);
  }

  obtenerPuertos() {
    return this.http.get<Puerto[]>(this.api + '/puertos');
  }

  obtenerPuertosNumero(numero : number) {
    return this.http.get<Puerto>(this.api + '/puerto/' + numero);
  }

  obtenerPuertosNombre(nombre : string) {
    return this.http.get<Puerto[]>(this.api + '/puertos/' + nombre);
  }

  actualizarPuerto(numero : number, puerto : Puerto) {
    return this.http.put<Puerto>(this.api + '/puerto/' + numero, puerto);
  }

  eliminarPuerto(id : number) {
    return this.http.delete<void>(this.api + '/puerto/' + id);
  }

}
