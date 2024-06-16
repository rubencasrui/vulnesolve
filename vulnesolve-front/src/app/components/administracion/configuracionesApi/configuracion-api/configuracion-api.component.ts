import {Component, Input, SimpleChanges} from '@angular/core';
import {ConfiguracionApiService} from "../../../../services/baseDatos/configuracion-api.service";
import {Puerto} from "../../../../models/dto/puerto";
import {HttpErrorResponse} from "@angular/common/http";
import {ConfiguracionApi} from "../../../../models/dto/configuracion-api";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-configuracion-api',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './configuracion-api.component.html',
  styleUrl: './configuracion-api.component.css'
})
export class ConfiguracionApiComponent {

  @Input("nombre")
  nombre !: string;
  configuracion !: ConfiguracionApi;
  encontrado : boolean;
  buscando : boolean;

  constructor(
    private configuracionApiService: ConfiguracionApiService
  ) {
    this.encontrado = false;
    this.buscando = true;
  }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.configuracionApiService.obtenerConfiguracionNombre(this.nombre)
      .subscribe({
        next: (configuracion : ConfiguracionApi) => {
          this.encontrado = true;
          this.configuracion = configuracion

          this.buscando = false;
        },
        error: (error : HttpErrorResponse) => {
          this.encontrado = false;

          this.buscando = false;
        }
      });
  }
}
