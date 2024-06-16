import { Component } from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {faSearch} from "@fortawesome/free-solid-svg-icons";
import {IconDefinition} from "@fortawesome/fontawesome-svg-core";
import {routes} from "../../app.routes";
import {UsuariosService} from "../../services/usuarios/usuarios.service";

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

  constructor(
    private router: Router,
    public usuariosService: UsuariosService
  ) {
    this.faSearch = faSearch;
    this.busqueda = "";
  }

  cerrarSesion() {
    localStorage.removeItem('token');
    this.usuariosService.actualizarValoresSesion();
    this.router.navigate(['iniciar-sesion']);
  }

  ngOnInit() {
    this.usuariosService.actualizarValoresSesion();
  }

}
