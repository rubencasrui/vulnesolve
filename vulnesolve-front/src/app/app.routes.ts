import { Routes } from '@angular/router';
import {InicioComponent} from "./components/inicio/inicio.component";
import {NmapComponent} from "./components/nmap/nmap.component";
import {VulnerabilidadesComponent} from "./components/vulnerabilidades/vulnerabilidades/vulnerabilidades.component";

export const routes: Routes = [
  {path: '', component: InicioComponent},

  {path: 'nmap', component: NmapComponent},

  {path: 'vulnerabilidades/:servicio', component: VulnerabilidadesComponent},

  {path: '**', redirectTo: '/', pathMatch: 'full'}
];
