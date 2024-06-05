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
    this.buscarVulnerabilidades();
  }

  buscarVulnerabilidades(): void {
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
          console.log("Volviendo a cargar vulnerabilidades");
          this.buscarVulnerabilidades();
        }
      });
  }

}
