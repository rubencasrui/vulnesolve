import { Component } from '@angular/core';
import {Estadistica} from "../../../models/estadistica/estadistica";
import {VulnerabilidadesService} from "../../../services/vulnerabilidades/vulnerabilidades.service";
import * as d3 from "d3";

@Component({
  selector: 'app-estadisticas-v3',
  standalone: true,
  imports: [],
  templateUrl: './estadisticas-v3.component.html',
  styleUrl: './estadisticas-v3.component.css'
})
export class EstadisticasV3Component {

  private severidadesV3 : Estadistica[];
  private svg: any;
  private margin =  50;
  private width  = 500;
  private height = 500;
  private radius = Math.min(this.width, this.height) / 2 - this.margin;
  private colors : any;

  constructor(
    private vulnerabilidadesService: VulnerabilidadesService
  ) {
    this.severidadesV3 = [
      {indice: "LOW", cantidad: 2742},
      {indice: "MEDIUM", cantidad: 63010},
      {indice: "HIGH", cantidad: 63798},
      {indice: "CRITICAL", cantidad: 23489}
    ];
  }

  ngOnInit() {
    this.cargarEstadisticasV3();

    let llamo: number = Date.now();
    let recivo : number = 0;
    this.vulnerabilidadesService.vulnerabilidadesVersion3()
      .subscribe(res => {
          this.severidadesV3 = res;
          this.cargarEstadisticasV3();
      })
      .add(() => {
        recivo = Date.now();
        console.log("V3: "+(recivo-llamo)/1000+" segundos");
      });
  }

  private cargarEstadisticasV3() {
      this.createSvg();
      this.createColors();
      this.drawChart();
    }

  private createSvg(): void {
    d3.select('figure#pie3').select('svg').remove();

    this.svg = d3.select("figure#pie3")
      .append("svg")
      .attr("width", this.width)
      .attr("height", this.height)
      .append("g")
      .attr(
        "transform",
        "translate(" + this.width / 2 + "," + this.height / 2 + ")"
      );

    // Añadir el título
    d3.select("figure#pie3 svg")
      .append("text")
      .attr("x", this.width / 2)
      .attr("y", this.height - this.margin/2)
      .attr("text-anchor", "middle")
      .style("font-size", "16px")
      .style("font-weight", "bold")
      .text("Vulnerabilidades con Severidad 3.x");

    // Añadir el total
    d3.select("figure#pie3 svg")
      .append("text")
      .attr("x", this.width / 2)
      .attr("y", this.height - this.margin/8)
      .attr("text-anchor", "middle")
      .style("font-size", "16px")
      .text("Vulnerabilidades en total: "+this.severidadesV3.reduce((a, b) => a + b.cantidad, 0));

    // Añadir la leyenda
    d3.select("figure#pie3 svg")
      .append("text")
      .attr("x", 0)
      .attr("y", 20)
      .attr("fill", "#ffdb4d")
      .style("font-weight", "bold")
      .style("font-size", "20px")
      .text("· " + (this.severidadesV3[0].cantidad/this.severidadesV3.reduce((a, b) => a + b.cantidad, 0)*100).toFixed(0) + "% BAJA");

    d3.select("figure#pie3 svg")
      .append("text")
      .attr("x", 0)
      .attr("y", 20*2)
      .attr("fill", "#ffaa00")
      .style("font-weight", "bold")
      .style("font-size", "20px")
      .text("· " + (this.severidadesV3[1].cantidad/this.severidadesV3.reduce((a, b) => a + b.cantidad, 0)*100).toFixed(0) + "% MEDIA");

    d3.select("figure#pie3 svg")
      .append("text")
      .attr("x", 0)
      .attr("y", 20*3)
      .attr("fill", "#ff5f5f")
      .style("font-weight", "bold")
      .style("font-size", "20px")
      .text("· " + (this.severidadesV3[2].cantidad/this.severidadesV3.reduce((a, b) => a + b.cantidad, 0)*100).toFixed(0) + "% ALTA");

    d3.select("figure#pie3 svg")
      .append("text")
      .attr("x", 0)
      .attr("y", 20*4)
      .attr("fill", "#b366ff")
      .style("font-weight", "bold")
      .style("font-size", "20px")
      .text("· " + (this.severidadesV3[3].cantidad/this.severidadesV3.reduce((a, b) => a + b.cantidad, 0)*100).toFixed(0) + "% CRÍTICA");
  }

  private createColors(): void {
    this.colors = d3.scaleOrdinal()
      .domain(this.severidadesV3.map(d => d.cantidad.toString()))
      .range(["#ffdb4d", "#ffaa00", "#ff5f5f", "#b366ff"]);
  }

  private drawChart(): void {
    // Compute the position of each group on the pie:
    const pie = d3.pie<any>().value((d: any) => Number(d.cantidad));

    // Build the pie chart
    this.svg
      .selectAll('pieces')
      .data(pie(this.severidadesV3))
      .enter()
      .append('path')
      .attr('d', d3.arc()
        .innerRadius(0)
        .outerRadius(this.radius)
      )
      .attr('fill', (d: any, i: any) => (this.colors(i)))
      .attr("stroke", "#121926")
      .style("stroke-width", "0.25px");

    // Add labels
    const labelLocation = d3.arc()
      .innerRadius(this.radius/4)
      .outerRadius(this.radius);

    this.svg
      .selectAll('pieces')
      .data(pie(this.severidadesV3))
      .enter()
      .append('text')
      .text((d: any)=> "←" + d.data.cantidad)
      .attr("transform", (d: any) => "translate(" + labelLocation.centroid(d) + ")")
      .style("font-weight", "bold");

  }
}
