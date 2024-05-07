import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {Escaneo} from "../models/escaneo";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class EscanearService {

  api : string = 'http://localhost:8080/media/';

  constructor(
    private http: HttpClient
  ) { }

  escanear(formData : FormData) : Observable<Escaneo> {
    var escaneo;

    escaneo = this.http.post<Escaneo>(this.api+'escanear', formData);

    return escaneo;
  }

  escaneoEstatico1() : Observable<Escaneo> {
    var escaneo;

    escaneo = this.http.get<Escaneo>(this.api+'escaneo/1714698167731_casa.xml');

    return escaneo;
  }

  escaneoEstatico2() : Observable<Escaneo> {
    var escaneo;

    escaneo = this.http.get<Escaneo>(this.api+'escaneo/1714740842306_google_i.xml');

    return escaneo;
  }
}
