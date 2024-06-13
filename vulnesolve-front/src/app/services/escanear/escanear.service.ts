import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {EscaneoNmap} from "../../models/nmap/escaneoNmap";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class EscanearService {

  api : string = environment.api;

  constructor(
    private http: HttpClient
  ) { }

  escanear(formData : FormData) : Observable<EscaneoNmap> {
    var escaneo;

    escaneo = this.http.post<EscaneoNmap>(this.api+'/media/escanear', formData);

    return escaneo;
  }

  escanearEjemplo(id : string) : Observable<EscaneoNmap> {
    var escaneo;

    escaneo = this.http.get<EscaneoNmap>(this.api+'/media/escaneo/'+id);

    return escaneo;
  }

  subirEscaneoEjemplo(formData : FormData) : Observable<EscaneoNmap> {
    var escaneo;

    escaneo = this.http.post<EscaneoNmap>(this.api+'/media/upload', formData);

    return escaneo;
  }

  verEscaneosEjempo() : Observable<string[]>{
    var escaneos;

    escaneos = this.http.get<string[]>(this.api+'/media/files');

    return escaneos;
  }

  verEscaneoEjemplo(nombre : string) : Observable<File> {
    var escaneo;

    // @ts-ignore
    escaneo = this.http.get<File>(this.api+'/media/files/'+nombre, {responseType: 'blob'});

    // @ts-ignore
    return escaneo;
  }

  eliminarEscaneoEjemplo(nombre : string) : Observable<void> {
    var escaneo;

    escaneo = this.http.delete<void>(this.api+'/media/delete/'+nombre);

    return escaneo;
  }
}
