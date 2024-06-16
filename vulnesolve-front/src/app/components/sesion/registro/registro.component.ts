import { Component } from '@angular/core';
import {NgbToast} from "@ng-bootstrap/ng-bootstrap";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {UsuariosService} from "../../../services/usuarios/usuarios.service";
import {Router} from "@angular/router";
import {PeticionCredenciales} from "../../../models/usuario/peticion-credenciales";
import {routes} from "../../../app.routes";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [
    NgbToast,
    ReactiveFormsModule
  ],
  templateUrl: './registro.component.html',
  styleUrl: './registro.component.css'
})
export class RegistroComponent {
  registrarForm: FormGroup;
  mensaje: string;
  show: boolean;

  constructor(
    private usuariosService: UsuariosService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.registrarForm = this.formBuilder.group({
      usuario: ['', [Validators.required]],
      clave: ['', [Validators.required]],
      confirmarClave: ['', [Validators.required]]
    });

    this.mensaje = '';
    this.show = false;
  }

  registrar() {
    if (this.registrarForm.invalid) {
      this.show = true;
      this.mensaje = 'Formulario incompleto';
    }
    else {
      if (this.registrarForm.value.clave == this.registrarForm.value.confirmarClave) {
        const credenciales: PeticionCredenciales = new PeticionCredenciales(this.registrarForm.value.usuario, this.registrarForm.value.clave);

        this.usuariosService.registro(credenciales).subscribe({
          next: (respuesta) => {
            this.show = true;
            this.mensaje = 'Usuario registrado con éxito, inicia sesión para continuar.';
          },
          error: (error : HttpErrorResponse) => {
            console.log(error);

            if(error.status === 409) {
              this.show = true;
              this.mensaje = 'Usuario ya registrado';
            }
            else{
              this.show = true;
              this.mensaje = 'Error en el servidor';
            }
          }
        });
      }
      else {
        this.show = true;
        this.mensaje = 'Las contraseñas no coinciden';
      }
    }
  }

}
