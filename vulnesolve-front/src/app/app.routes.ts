import { Routes } from '@angular/router';
import {InicioComponent} from "./components/inicio/inicio.component";
import {NmapComponent} from "./components/nmap/nmap.component";

export const routes: Routes = [
  {path: '', component: InicioComponent},

  {path: 'nmap', component: NmapComponent},

  {path: '**', redirectTo: '/', pathMatch: 'full'}
];
