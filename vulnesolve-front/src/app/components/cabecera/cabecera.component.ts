import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {faSearch} from "@fortawesome/free-solid-svg-icons";
import {IconDefinition} from "@fortawesome/fontawesome-svg-core";

@Component({
  selector: 'app-cabecera',
  standalone: true,
    imports: [
        RouterLink,
        FaIconComponent,
        FormsModule,
        ReactiveFormsModule
    ],
  templateUrl: './cabecera.component.html',
  styleUrl: './cabecera.component.css'
})
export class CabeceraComponent {

  faSearch : IconDefinition;
  busqueda: string;

  constructor() {
    this.faSearch = faSearch;
    this.busqueda = "";
  }
}
