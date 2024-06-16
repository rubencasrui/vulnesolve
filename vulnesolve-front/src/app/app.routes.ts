import { Routes } from '@angular/router';
import {InicioComponent} from "./components/inicio/inicio.component";
import {NmapComponent} from "./components/nmap/nmap.component";
import {VulnerabilidadesComponent} from "./components/vulnerabilidades/vulnerabilidades/vulnerabilidades.component";
import {PuertosComponent} from "./components/puertos/puertos/puertos.component";
import {PuertoComponent} from "./components/puertos/puerto/puerto.component";
import {CrearPuertoComponent} from "./components/puertos/crear-puerto/crear-puerto.component";
import {EditarPuertoComponent} from "./components/puertos/editar-puerto/editar-puerto.component";
import {AdministracionComponent} from "./components/administracion/administracion/administracion.component";
import {
  CrearConfiguracionApiComponent
} from "./components/administracion/configuracionesApi/crear-configuracion-api/crear-configuracion-api.component";
import {
  EditarConfiguracionApiComponent
} from "./components/administracion/configuracionesApi/editar-configuracion-api/editar-configuracion-api.component";
import {
  ConfiguracionApiComponent
} from "./components/administracion/configuracionesApi/configuracion-api/configuracion-api.component";
import {LoginComponent} from "./components/sesion/login/login.component";
import {RegistroComponent} from "./components/sesion/registro/registro.component";
import {adminGuard} from "./guards/admin.guard";
import {usuarioGuard} from "./guards/usuario.guard";
import {sinSesionGuard} from "./guards/sin-sesion.guard";

export const routes: Routes = [
  {path: '', component: InicioComponent},

  {path: 'nmap', component: NmapComponent},

  {path: 'puertos', component: PuertosComponent},
  {path: 'puerto/:numero', component: PuertoComponent},

  {path: 'vulnerabilidades/:servicio', component: VulnerabilidadesComponent},

  {path: 'iniciar-sesion', component: LoginComponent, canActivate: [sinSesionGuard]},
  {path: 'registrar', component: RegistroComponent, canActivate: [sinSesionGuard]},

  {path: 'administracion', component: AdministracionComponent, canActivate: [adminGuard]},

  {path: 'crear-puerto', component: CrearPuertoComponent, canActivate: [adminGuard]},
  {path: 'editar-puerto/:numero', component: EditarPuertoComponent, canActivate: [adminGuard]},

  {path: 'configuracion-api/:nombre', component: ConfiguracionApiComponent, canActivate: [adminGuard]},
  {path: 'crear-configuracion-api', component: CrearConfiguracionApiComponent, canActivate: [adminGuard]},
  {path: 'editar-configuracion-api/:nombre', component: EditarConfiguracionApiComponent, canActivate: [adminGuard]},

  {path: '**', component: InicioComponent}
];
