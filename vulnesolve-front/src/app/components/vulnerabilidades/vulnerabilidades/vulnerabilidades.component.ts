import {Component, Input, SimpleChanges} from '@angular/core';
import {VulnerabilidadesService} from "../../../services/vulnerabilidades/vulnerabilidades.service";
import {JsonVulneSolve} from "../../../models/vulnerabilidades/json-vulne-solve";
import {
  faArrowUpRightFromSquare,
  faBarsProgress,
  faRectangleList,
  faSearch, faSpinner
} from "@fortawesome/free-solid-svg-icons";
import {NgbPagination, NgbRating} from "@ng-bootstrap/ng-bootstrap";
import {SlicePipe} from "@angular/common";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {IconDefinition} from "@fortawesome/fontawesome-svg-core";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-vulnerabilidades',
  standalone: true,
  imports: [
    NgbRating,
    SlicePipe,
    FaIconComponent,
    NgbPagination,
    FormsModule
  ],
  templateUrl: './vulnerabilidades.component.html',
  styleUrl: './vulnerabilidades.component.css'
})
export class VulnerabilidadesComponent {

  @Input('servicio') servicio: string;
  vulnerabilidades: JsonVulneSolve;
  cargando: boolean;
  page : number;
  pageSize : number;

  faSearch : IconDefinition;
  faArrowUpRightFromSquare : IconDefinition;
  faRectangleList : IconDefinition;
  faBarsProgress : IconDefinition;
  faSpinner : IconDefinition;

  constructor(
    private vulnerabilidadesService: VulnerabilidadesService,
  ) {
    this.servicio = "";
    this.vulnerabilidades = new JsonVulneSolve("", 0, "", 0, []);
    this.cargando = true;
    this.page = 1;
    this.pageSize = 1;

    this.faSearch = faSearch;
    this.faArrowUpRightFromSquare = faArrowUpRightFromSquare;
    this.faRectangleList = faRectangleList;
    this.faBarsProgress = faBarsProgress;
    this.faSpinner = faSpinner;
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log(changes)
    this.buscarVulnerabilidades(5);
  }

  buscarVulnerabilidades(intentos : number): void {
    let llamo: number = Date.now();
    let recivo : number = 0;

    console.log("cargando vulnerabilidades " + this.servicio);
    this.cargando = true;
    this.vulnerabilidadesService.vulnerabilidades(this.servicio)
      .subscribe(vulnerabilidades => {
        this.vulnerabilidades = vulnerabilidades;
        this.cargando = false;
        console.log("vulnerabilidades cargadas " + this.vulnerabilidades);
      })
      .add(() => {
        if (this.cargando){
          if (intentos > 0){
            console.log("Volviendo a cargar vulnerabilidades, quedan " + (intentos-1) + " intentos.");
            this.buscarVulnerabilidades(intentos-1);
          }
          else{
            this.vulnerabilidades = new JsonVulneSolve(this.servicio, 0, "", -1, []);
            this.cargando = false;
          }
        }
        else {
          recivo = Date.now();
          console.log(this.servicio+": "+(recivo-llamo)/1000+" segundos");
        }
      });
  }

}
