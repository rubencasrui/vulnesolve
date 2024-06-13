import {Component, Input, SimpleChanges} from '@angular/core';
import {Puerto} from "../../../models/dto/puerto";
import {PuertosService} from "../../../services/baseDatos/puertos.service";
import {NgbPagination} from "@ng-bootstrap/ng-bootstrap";
import {RouterLink} from "@angular/router";
import {SlicePipe} from "@angular/common";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-puerto',
  standalone: true,
  imports: [
    NgbPagination,
    RouterLink,
    SlicePipe
  ],
  templateUrl: './puerto.component.html',
  styleUrl: './puerto.component.css'
})
export class PuertoComponent {

  @Input("numero") numero !: number;
  puerto !: Puerto;
  encontrado : boolean;

  constructor(
    private puertosService: PuertosService
  ) {
    this.encontrado = false;
  }

  ngOnInit(): void {
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

}
