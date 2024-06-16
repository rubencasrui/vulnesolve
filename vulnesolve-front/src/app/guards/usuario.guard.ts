import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {UsuariosService} from "../services/usuarios/usuarios.service";

export const usuarioGuard: CanActivateFn = (route, state) => {

  const router = inject(Router);
  const usuariosService = inject(UsuariosService);

  usuariosService.actualizarValoresSesion();
  if (usuariosService.sesionIniciada) {
    return true;
  }
  else {
    return router.createUrlTree(['']);
  }
};
