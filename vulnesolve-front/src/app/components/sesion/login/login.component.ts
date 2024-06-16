import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {UsuariosService} from "../../../services/usuarios/usuarios.service";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {NgbToast} from "@ng-bootstrap/ng-bootstrap";
import {routes} from "../../../app.routes";
import {PeticionCredenciales} from "../../../models/usuario/peticion-credenciales";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgbToast
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup;
  mensaje: string;
  show: boolean;

  constructor(
    private usuariosService: UsuariosService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.loginForm = this.formBuilder.group({
      usuario: ['', [Validators.required]],
      clave: ['', [Validators.required]]
    });

    this.mensaje = '';
    this.show = false;
  }

  login() {
    if (this.loginForm.invalid) {
      this.show = true;
      this.mensaje = 'Formulario incompleto';
    }
    else {
      const credenciales: PeticionCredenciales = new PeticionCredenciales(this.loginForm.value.usuario, this.loginForm.value.clave);

      this.usuariosService.login(credenciales).subscribe({
        next: (respuesta) => {
          console.log(respuesta);

          localStorage.setItem('token', respuesta.token);
          this.usuariosService.actualizarValoresSesion();
          this.router.navigate([routes[0].path]);
        },
        error: (error : HttpErrorResponse) => {
          if (error.status === 401) {
            console.log(error);

            this.show = true;
            this.mensaje = error.error.mensaje;
          } else {
            console.log(error);

            this.show = true;
            this.mensaje = 'Error inesperado';
          }
        }
      });
    }
  }

}
