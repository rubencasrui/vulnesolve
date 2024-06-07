import {Component, inject} from '@angular/core';
import {VulnerabilidadesService} from "../../../services/vulnerabilidades/vulnerabilidades.service";
import {Estadistica} from "../../../models/estadistica/estadistica";
import * as d3 from 'd3';

@Component({
  selector: 'app-estadisticas-v2',
  standalone: true,
  imports: [],
  templateUrl: './estadisticas-v2.component.html',
  styleUrl: './estadisticas-v2.component.css'
})
export class EstadisticasV2Component {

  private severidadesV2 : Estadistica[];
  private svg: any;
  private margin =  50;
  private width  = 500;
  private height = 500;
  // The radius of the pie chart is half the smallest side
  private radius = Math.min(this.width, this.height) / 2 - this.margin;
  private colors : any;

  constructor(
    private vulnerabilidadesService: VulnerabilidadesService
  ) {
    this.severidadesV2 = [
      {indice: "LOW", cantidad: 19074},
      {indice: "MEDIUM", cantidad: 104167},
      {indice: "HIGH", cantidad: 56836}
    ];

  }

  ngOnInit() {
    this.cargarEstadisticasV2();

    let llamo: number = Date.now();
    let recivo : number = 0;
    this.vulnerabilidadesService.vulnerabilidadesVersion2()
      .subscribe(res => {
        this.severidadesV2 = res;
        this.cargarEstadisticasV2();
      })
      .add(() => {
        recivo = Date.now();
        console.log("V2: "+(recivo-llamo)/1000+" segundos");
      });
  }

  private cargarEstadisticasV2() {
    this.createSvg();
    this.createColors();
    this.drawChart();
  }

  private createSvg(): void {
    d3.select('figure#pie2').select('svg').remove();

    this.svg = d3.select("figure#pie2")
      .append("svg")
      .attr("width", this.width)
      .attr("height", this.height)
      .append("g")
      .attr(
        "transform",
        "translate(" + this.width / 2 + "," + this.height / 2 + ")"
      );

    // Añadir el título
    d3.select("figure#pie2 svg")
      .append("text")
      .attr("x", this.width / 2)
      .attr("y", this.height - this.margin/2)
      .attr("text-anchor", "middle")
      .style("font-size", "16px")
      .style("font-weight", "bold")
      .text("Vulnerabilidades con Severidad 2.0");
  }

  private createColors(): void {
    this.colors = d3.scaleOrdinal()
      .domain(this.severidadesV2.map(d => d.cantidad.toString()))
      .range(["#ffdb4d", "#ffaa00", "#ff5f5f"]);
  }

  private drawChart(): void {
    // Compute the position of each group on the pie:
    const pie = d3.pie<any>().value((d: any) => Number(d.cantidad));

    // Build the pie chart
    this.svg
      .selectAll('pieces')
      .data(pie(this.severidadesV2))
      .enter()
      .append('path')
      .attr('d', d3.arc()
        .innerRadius(0)
        .outerRadius(this.radius)
      )
      .attr('fill', (d: any, i: any) => (this.colors(i)))
      .attr("stroke", "#121926")
      .style("stroke-width", "1px");

    // Add labels
    const labelLocation = d3.arc()
      .innerRadius(100)
      .outerRadius(this.radius);

    this.svg
      .selectAll('pieces')
      .data(pie(this.severidadesV2))
      .enter()
      .append('text')
      .text((d: any)=> d.data.indice + ": " + d.data.cantidad)
      .attr("transform", (d: any) => "translate(" + labelLocation.centroid(d) + ")")
      .style("text-anchor", "middle");
  }

}
