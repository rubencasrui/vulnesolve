import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {ConfiguracionApi} from "../../models/dto/configuracion-api";

@Injectable({
  providedIn: 'root'
})
export class ConfiguracionApiService {

  api : string = environment.api

  constructor(
    private http: HttpClient
  ) { }

  crearConfiguracion(configuracion : ConfiguracionApi){
    return this.http.post<ConfiguracionApi>(this.api + '/configuracionApi', configuracion);
  }

  obtenerConfiguraciones(){
      return this.http.get<ConfiguracionApi[]>(this.api + '/configuracionApi');
  }

  obtenerConfiguracionId(id : number){
      return this.http.get<ConfiguracionApi>(this.api + '/configuracionApi/' + id);
  }

  obtenerConfiguracionNombre(nombre : string){
        return this.http.get<ConfiguracionApi>(this.api + '/configuracionApi/nombre/' + nombre);
  }

  actualizarConfiguracion(configuracion : ConfiguracionApi){
      return this.http.put<ConfiguracionApi>(this.api + '/configuracionApi/' + configuracion.nombre, configuracion);
  }

  eliminarConfiguracion(nombre : string){
      return this.http.delete<void>(this.api + '/configuracionApi/' + nombre);
  }

}
