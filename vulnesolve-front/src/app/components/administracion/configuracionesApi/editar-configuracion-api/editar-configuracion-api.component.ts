import {Component, Input, SimpleChanges} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgbToast} from "@ng-bootstrap/ng-bootstrap";
import {ConfiguracionApi} from "../../../../models/dto/configuracion-api";
import {HttpErrorResponse} from "@angular/common/http";
import {ConfiguracionApiService} from "../../../../services/baseDatos/configuracion-api.service";

@Component({
  selector: 'app-editar-configuracion-api',
  standalone: true,
  imports: [
    FormsModule,
    NgbToast,
    ReactiveFormsModule
  ],
  templateUrl: './editar-configuracion-api.component.html',
  styleUrl: './editar-configuracion-api.component.css'
})
export class EditarConfiguracionApiComponent {

  @Input("nombre")
  nombre !: string;
  configuracion !: ConfiguracionApi;
  encontrado : boolean;

  show: boolean;
  mensaje: string;

  constructor(
    private configuracionApiService: ConfiguracionApiService
  ) {
    this.encontrado = false;

    this.show = false;
    this.mensaje = "";
  }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.configuracionApiService.obtenerConfiguracionNombre(this.nombre)
      .subscribe({
        next: (configuracion : ConfiguracionApi) => {
          this.encontrado = true;
          this.configuracion = configuracion
        },
        error: (error : HttpErrorResponse) => {
          this.encontrado = false;
        }
      });
  }

  modificarConfiguracion(): void {
    this.configuracionApiService.actualizarConfiguracion(this.configuracion)
      .subscribe({
        next: (configuracionModificada : ConfiguracionApi) => {
          this.encontrado = true;
          this.configuracion = configuracionModificada;

          this.show = true;
          this.mensaje = "Configuración modificada con éxito";
        },
        error: (error : HttpErrorResponse) => {
          this.show = true;
          if (error.status == 404) {
            this.mensaje = "La configuración no existe";
          }
          else {
            this.mensaje = "Error al modificar la configuración";
          }
        }
      });
  }

}
