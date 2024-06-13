import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgbToast} from "@ng-bootstrap/ng-bootstrap";
import {Puerto} from "../../../models/dto/puerto";
import {PuertosService} from "../../../services/baseDatos/puertos.service";

@Component({
  selector: 'app-crear-puerto',
  standalone: true,
  imports: [
    FormsModule,
    NgbToast
  ],
  templateUrl: './crear-puerto.component.html',
  styleUrl: './crear-puerto.component.css'
})
export class CrearPuertoComponent {

  puerto !: Puerto;
  show: boolean;
  mensaje: string;

  constructor(
    private puertosService: PuertosService
  ) {
    this.puerto = new Puerto(0, "", "");
    this.show = false;
    this.mensaje = "";
  }

  crearPuerto(): void {
    this.puertosService.crearPuerto(this.puerto)
      .subscribe({
        next: (puertoCreado : Puerto) => {
          this.show = true;
          this.mensaje = "Puerto creado con éxito";
          this.puerto = new Puerto(0, "", "");
        },
        error: (error) => {
          this.show = true;
          if (error.status == 409) {
            this.mensaje = "El puerto ya existe";
          }
          else {
            this.mensaje = "Error al crear el puerto";
          }
        }
      });
  }

}
