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

export const routes: Routes = [
  {path: '', component: InicioComponent},

  {path: 'nmap', component: NmapComponent},

  {path: 'vulnerabilidades/:servicio', component: VulnerabilidadesComponent},

  {path: 'puertos', component: PuertosComponent},
  {path: 'puerto/:numero', component: PuertoComponent},

  {path: 'administracion', component: AdministracionComponent},

  {path: 'crear-puerto', component: CrearPuertoComponent},
  {path: 'editar-puerto/:numero', component: EditarPuertoComponent},

  {path: 'configuracion-api/:nombre', component: ConfiguracionApiComponent},
  {path: 'crear-configuracion-api', component: CrearConfiguracionApiComponent},
  {path: 'editar-configuracion-api/:nombre', component: EditarConfiguracionApiComponent},


  {path: '**', redirectTo: '/', pathMatch: 'full'}
];
