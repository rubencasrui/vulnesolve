import {Component, Input, SimpleChanges} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgbToast} from "@ng-bootstrap/ng-bootstrap";
import {Puerto} from "../../../models/dto/puerto";
import {PuertosService} from "../../../services/baseDatos/puertos.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-editar-puerto',
  standalone: true,
  imports: [
    FormsModule,
    NgbToast,
    ReactiveFormsModule
  ],
  templateUrl: './editar-puerto.component.html',
  styleUrl: './editar-puerto.component.css'
})
export class EditarPuertoComponent {

  @Input("numero")
  numero !: number;
  puerto : Puerto;
  encontrado: boolean;
  show: boolean;
  mensaje: string;

  constructor(
    private puertosService: PuertosService
  ) {
    this.puerto = new Puerto(0, "", "");
    this.encontrado = false;
    this.show = false;
    this.mensaje = "";
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.puertosService.obtenerPuertosNumero(this.numero)
      .subscribe({
        next: (puerto : Puerto) => {
          this.encontrado = true;
          this.puerto = puerto
        },
        error: (error : HttpErrorResponse) => {
          this.encontrado = false;
        }
      });
  }

  editarPuerto(): void {
    this.puertosService.actualizarPuerto(this.numero, this.puerto)
      .subscribe({
        next: (puertoEditado : Puerto) => {
          this.show = true;
          this.mensaje = "Puerto editado con éxito";
          this.puerto = puertoEditado;
        },
        error: (error) => {
          this.show = true;
          if (error.status == 404) {
            this.mensaje = "El puerto no existe";
          }
          else {
            this.mensaje = "Error al editar el puerto";
          }
        }
      });
  }

}
