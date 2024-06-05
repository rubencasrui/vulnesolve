import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";
import {NgOptimizedImage} from "@angular/common";
import {EstadisticasV2Component} from "../estadisticas/estadisticas-v2/estadisticas-v2.component";
import {EstadisticasV3Component} from "../estadisticas/estadisticas-v3/estadisticas-v3.component";

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [
    RouterLink,
    NgOptimizedImage,
    EstadisticasV2Component,
    EstadisticasV3Component
  ],
  templateUrl: './inicio.component.html',
  styleUrl: './inicio.component.css'
})
export class InicioComponent {

  constructor() {

  }

  ngOnInit() {
  }

}
