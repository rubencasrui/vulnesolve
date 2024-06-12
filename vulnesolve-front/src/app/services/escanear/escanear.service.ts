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

  escaneos() : Observable<String>{
    var escaneos;

    // @ts-ignore
    escaneos = this.http.get<String>(this.api+'/media/files', {responseType: 'text'});

    // @ts-ignore
    return escaneos;
  }

  escaneo(id : string) : Observable<EscaneoNmap> {
    var escaneo;

    escaneo = this.http.get<EscaneoNmap>(this.api+'/media/escaneo/'+id);

    return escaneo;
  }
}
