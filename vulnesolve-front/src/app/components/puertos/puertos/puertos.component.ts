import { Component } from '@angular/core';
import {PuertosService} from "../../../services/baseDatos/puertos.service";
import {Puerto} from "../../../models/dto/puerto";
import {SlicePipe} from "@angular/common";
import {NgbPagination} from "@ng-bootstrap/ng-bootstrap";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-puertos',
  standalone: true,
  imports: [
    SlicePipe,
    NgbPagination,
    RouterLink
  ],
  templateUrl: './puertos.component.html',
  styleUrl: './puertos.component.css'
})
export class PuertosComponent {

  puertos : Puerto[];
  page = 1;
  pageSize = 6;

  constructor(
    private puertosService: PuertosService
  ) {
    this.puertos = [];
  }

  ngOnInit(): void {
    this.puertosService.obtenerPuertos().subscribe(puertos => {
        this.puertos = puertos;
      });
  }

}
