import { Component } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgbToast} from "@ng-bootstrap/ng-bootstrap";
import {ConfiguracionApi} from "../../../../models/dto/configuracion-api";
import {ConfiguracionApiService} from "../../../../services/baseDatos/configuracion-api.service";

@Component({
  selector: 'app-crear-configuracion-api',
  standalone: true,
  imports: [
    FormsModule,
    NgbToast,
    ReactiveFormsModule
  ],
  templateUrl: './crear-configuracion-api.component.html',
  styleUrl: './crear-configuracion-api.component.css'
})
export class CrearConfiguracionApiComponent {

  configuracion : ConfiguracionApi;
  show: boolean;
  mensaje: string;

  constructor(
    private configuracionService: ConfiguracionApiService
  ) {
    this.configuracion = new ConfiguracionApi(0, "", 1, 1, false, 0);
    this.show = false;
    this.mensaje = "";
  }

  crearConfiguracion() {
    this.configuracionService.crearConfiguracion(this.configuracion)
      .subscribe(
        {
          next: (configuracionCreada : ConfiguracionApi) => {
            this.show = true;
            this.mensaje = "Configuración creada con éxito";
            this.configuracion = new ConfiguracionApi(0, "", 0, 0, false, 0);
          },
          error: (error) => {
            this.show = true;
            if (error.status == 409) {
              this.mensaje = "La configuración ya existe";
            }
            else {
              this.mensaje = "Error al crear la configuración";
            }
          }
        }
      );
  }

}
