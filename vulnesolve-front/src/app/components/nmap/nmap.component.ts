import {Component, ElementRef, HostListener} from '@angular/core';
import { FormsModule } from "@angular/forms";
import * as d3 from 'd3';
import { EscanearService } from "../../services/escanear.service";
import { NgbToast } from "@ng-bootstrap/ng-bootstrap";
import {Equipo} from "../../models/equipo";
import {Union} from "../../models/union";
import {Escaneo} from "../../models/escaneo";
import {Puerto} from "../../models/puerto";
import {SlicePipe} from "@angular/common";

@Component({
  selector: 'app-nmap',
  standalone: true,
  imports: [
    FormsModule,
    NgbToast,
    SlicePipe
  ],
  templateUrl: './nmap.component.html',
  styleUrl: './nmap.component.css'
})
export class NmapComponent {

  formData : FormData;
  show : boolean;
  titulo : string;
  mensaje : string;
  escaneo : Escaneo;

  constructor(
    private escanearService: EscanearService,
    public elementRef: ElementRef
  ) {
    this.formData = new FormData();
    this.show = false;
    this.titulo = '';
    this.mensaje = '';
    this.escaneo = new Escaneo([], []);
  }

  ngOnInit() {
  }

  ejemplo1() {
    this.escanearService.escaneoEstatico1().subscribe(escaneo => {
      this.escaneo = escaneo;
      this.cargarGrafo();
    })
  }

  ejemplo2() {
    this.escanearService.escaneoEstatico2().subscribe(escaneo => {
      this.escaneo = escaneo;
      this.cargarGrafo();
    })
  }

  compareEquipos(a : Equipo, b : Equipo) : number {
    var x = parseInt(a.id);
    var y = parseInt(b.id);

    return x - y;
  }

  comparePuertos(a : Puerto, b : Puerto) : number {
    return a.numero - b.numero;
  }

  getFile(event: Event) {
    const target = event.target as HTMLInputElement;
    if (target.files && target.files[0]){
      var escaneo : File = target.files[0];

      this.formData.set('file', escaneo);

      this.titulo = 'Archivo seleccionado';
      this.mensaje =
        `Nombre: ${escaneo.name}\n` +
        `Tipo: ${escaneo.type}\n` +
        `Tamaño: ${escaneo.size} bytes\n` +
        `Creado: ${(new Date(escaneo.lastModified)).toString().substring(0, 24)}\n`;
    }
    else {
      this.titulo = 'Error al seleccionar archivo';
      this.mensaje = 'Por favor, seleccione un archivo.';
      this.show = true;
    }
  }

  escanear() {
    this.escanearService.escanear(this.formData)
      .subscribe(escaneo => {
        this.escaneo = escaneo;
        this.drawNetworkTopology(this.escaneo);
      });
  }

  @HostListener('window:resize', ['$event'])
  cargarGrafo() : void {
    if (this.escaneo.equipos.length !== 0){
      this.drawNetworkTopology(this.escaneo);
    }
  }

  drawNetworkTopology(escaneo : Escaneo): void {

    var nodes = escaneo.equipos;
    var links = escaneo.uniones;

    let svg !: any;
    let link !: any;
    let node !: any;
    let ip !: any;
    let mac !: any;
    let puertos !: any;
    let simulation !: any;

    let anchoTotal = this.elementRef.nativeElement.offsetWidth;

    let width      = anchoTotal;
    let height     = 600;

    let ancho      = 100;
    let alto       = 100;
    let borde      =  10;
    let letra      =  12;
    let colision   =  (ancho + alto) / 2 * 0.50 * Math.sqrt(2);
    let carga      =   0;

    let gris  = '#BFBFBF';
    let verde = '#9ED89E';
    let amarillo = '#EEEE88';
    let naranja = '#FFB084';
    let rojo = '#FF3333';
    let rojo_oscuro = '#8B0000';

    d3.select('figure').selectAll('svg').remove();

    svg = d3.select('figure')
      .append('svg')
      .attr('width', width)
      .attr('height', height);

    link = svg
      .append('g')
      .attr('grupo', 'lineas')
      .selectAll('line')
      .data(links)
      .enter()
      .append('line')
      .attr('stroke', 'blue')
      .attr('stroke-width', 5)
      .attr('stroke-opacity', 0.50);

    node = svg
      .append('g')
      .attr('grupo', 'nodos')
      .selectAll('rect')
      .data(nodes)
      .enter()
      .append('rect')
      .attr('id', (d: any) => d.id)
      .attr('ip', (d: any) => d.ip)
      .attr('width', ancho)
      .attr('height', alto)
      .attr('rx', borde)
      .attr('ry', borde)
      .style('cursor', 'move')
      .attr('fill',
        (d: any) =>
          d.id === 0 ?
            gris :
          d.tipo === 0 ?
            verde :
          d.tipo === 1 ?
            amarillo :
          d.tipo === 2 ?
            naranja :
          d.tipo === 3 ?
            rojo :
            rojo_oscuro
      )
      .call(d3.drag()
        .on('start', (event: any, d: any) => {
          d.fx = d.x;	// Fija la posicion en X del nodo al arrastrarlo
          d.fy = d.y;	// Fija la posicion en Y del nodo al arrastrarlo
          // quitar fuerzas de la simulacion
          simulation.force('link'  , null);
          simulation.force('charge', null);
          simulation.force('center', null);
        })
        .on('drag', (event: any, d: any) => {
          d.fx = event.x;	// Actualiza la posicion en X del nodo al arrastrarlo
          d.fy = event.y;	// Actualiza la posicion en Y del nodo al arrastrarlo
          // reiniciar la simulacion
          simulation.restart();
        })
        .on('end', (event: any, d: any) => {
          d.fx = null;	// Elimina la posicion en X del nodo al soltarlo
          d.fy = null;	// Elimina la posicion en Y del nodo al soltarlo
          // parar la simulacion
          simulation.stop();
        })
      );

    ip = svg
      .append('g')
      .attr('grupo', 'ip')
      .selectAll('text')
      .data(nodes)
      .enter()
      .append('text')
      .attr('id', (d: any) => d.id)
      .attr('fill', 'black')
      .attr('text-anchor', 'middle')
      .attr('dominant-baseline', 'middle')
      .text((d: any) => d.ip)
      .style('font-size', letra+'px')
      .style('font-family', 'Arial')
      .style('font-weight', 'bold')
      .style('cursor', 'pointer')
      .style('text-shadow', '0px 0px 2px white')
      .on('click', (event: any, d: any) => {
      });

    mac = svg
      .append('g')
      .attr('grupo', 'mac')
      .selectAll('text')
      .data(nodes)
      .enter()
      .append('text')
      .attr('id', (d: any) => d.id)
      .attr('fill', 'black')
      .attr('text-anchor', 'middle')
      .attr('dominant-baseline', 'middle')
      .text((d: any) => d.mac)
      .style('font-size', letra-2+'px')
      .style('font-family', 'Arial')
      .style('font-weight', 'bold')
      .style('text-shadow', '0px 0px 2px white')

    puertos = svg
      .append('g')
      .attr('grupo', 'puertos')
      .selectAll('text')
      .data(nodes)
      .enter()
      .append('text')
      .attr('id', (d: any) => d.id)
      .attr('fill', 'black')
      .attr('text-anchor', 'middle')
      .attr('dominant-baseline', 'middle')
      .text((d: any) => d.puertos.length == 0 ? '' : d.puertos.length + ' puertos abiertos')
      .style('font-size', letra-2+'px')
      .style('font-family', 'Arial')
      .style('font-weight', 'bold')
      .style('text-shadow', '0px 0px 2px white')

    simulation = d3.forceSimulation(nodes)
      .force('link',   d3.forceLink(links).id((d: any) => d.id))
      .force('charge', d3.forceManyBody().strength(carga))
      .force('collide', d3.forceCollide(colision))
      .force('center', d3.forceCenter(width/2 - ancho/2, height/2 - alto/2));

    simulation.on('tick', () => {
      nodes.forEach(this.forceContain(width, height, ancho, alto));

      link
        .attr('x1', (d: any) => d.source.x + ancho/2)
        .attr('y1', (d: any) => d.source.y + alto/2)
        .attr('x2', (d: any) => d.target.x + ancho/2)
        .attr('y2', (d: any) => d.target.y + alto/2);

      node
        .attr('x', (d: any) => d.x)
        .attr('y', (d: any) => d.y);

      ip
        .attr('x', (d: any) => d.x + ancho/2)
        .attr('y', (d: any) => d.id == 0 ? d.y + alto/2 : d.y + 0*letra + letra);

      mac
        .attr('x', (d: any) => d.x + ancho/2)
        .attr('y', (d: any) => d.y + 2*letra + letra);

      puertos
        .attr('x', (d: any) => d.x + ancho/2)
        .attr('y', (d: any) => d.y + 4*letra + letra);
    });
  }

  forceContain(width: number, height: number, ancho: number, alto: number) {
    return function(d: any) {
      // limitar la posicion de los nodos dentro del area de dibujo

      // max de la mitad del ancho del nodo y la mitad del ancho del area de dibujo
      d.x = Math.max(0, Math.min(width - ancho, d.x));

      d.y = Math.max(0, Math.min(height - alto, d.y));
    }
  }

}