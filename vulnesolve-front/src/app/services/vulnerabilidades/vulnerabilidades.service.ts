import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {TotalResults} from "../../models/estadistica/total-results";
import {JsonVulneSolve} from "../../models/vulnerabilidades/json-vulne-solve";
import {map, Observable, of} from "rxjs";
import {Estadistica} from "../../models/estadistica/estadistica";

@Injectable({
  providedIn: 'root'
})
export class VulnerabilidadesService {

  api : string = environment.api;

  constructor(
    private http: HttpClient
  ) { }

  vulnerabilidades(busqueda : string) {
    return this.http.get<JsonVulneSolve>(this.api + '/nve/vulnerabilidades/'+busqueda);
  }

  cantidadVulnerabilidades(busqueda : string) {
    return this.http.get<TotalResults>(this.api + '/nve/estadisticas/vulnerabilidades/'+busqueda);
  }

  vulnerabilidadesVersion2() {
    return this.http.get<Estadistica[]>(this.api + '/nve/estadisticas/severidad/v2');
  }

  vulnerabilidadesVersion3() {
    return this.http.get<Estadistica[]>(this.api + '/nve/estadisticas/severidad/v3');
  }

}
