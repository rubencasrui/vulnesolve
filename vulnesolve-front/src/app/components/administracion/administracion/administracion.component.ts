import { Component } from '@angular/core';
import {PuertosService} from "../../../services/baseDatos/puertos.service";
import {RouterLink} from "@angular/router";
import {Puerto} from "../../../models/dto/puerto";
import {FormsModule} from "@angular/forms";
import {NgbModal, NgbToast} from "@ng-bootstrap/ng-bootstrap";
import {ConfiguracionApi} from "../../../models/dto/configuracion-api";
import {ConfiguracionApiService} from "../../../services/baseDatos/configuracion-api.service";
import {EscanearService} from "../../../services/escanear/escanear.service";
import {UsuariosService} from "../../../services/usuarios/usuarios.service";
import {Usuario} from "../../../models/usuario/usuario";

@Component({
  selector: 'app-administracion',
  standalone: true,
  imports: [
    RouterLink,
    FormsModule,
    NgbToast
  ],
  templateUrl: './administracion.component.html',
  styleUrl: './administracion.component.css'
})
export class AdministracionComponent {

  numero : number | undefined;
  puertos : Puerto[];

  configuracion : string | undefined;
  configuraciones : ConfiguracionApi[];

  escaneoEjemplo : string | undefined;
  escaneosEjemplo : string[];
  formData : FormData;

  usuario: string | undefined;
  usuarios: Usuario[];

  show: boolean;
  mensaje: string;

  constructor(
    private puertosService: PuertosService,
    private configuracionApiService: ConfiguracionApiService,
    private escanearService: EscanearService,
    private usuariosService: UsuariosService,
    private modalService: NgbModal
  ) {
    this.puertos = [];
    this.configuraciones = [];
    this.escaneosEjemplo = [];
    this.usuarios = [];
    this.formData = new FormData();

    this.show = false;
    this.mensaje = "";
  }

  ngOnInit(): void {
    this.obtenerPuertos();
    this.obtenerConfiguraciones();
    this.obtenerEscaneosEjemplo();
    this.obtenerUsuariosNoAdmin();
  }

  obtenerPuertos(): void {
    this.puertosService.obtenerPuertos().subscribe(puertos => {
      this.puertos = puertos;
      if (this.puertos.length > 0) {
        this.numero = this.puertos[0].numero;
      }
    });
  }

  eliminarPuerto() {
    if (this.numero){
      this.puertosService.eliminarPuerto(this.numero)
        .subscribe({
          next: () => {
            this.obtenerPuertos();
            this.numero = undefined;

            this.show = true;
            this.mensaje = "Puerto eliminado con éxito";
          },
          error: (error) => {
            this.show = true;
            this.mensaje = "Error al eliminar el puerto";
          }
        });
    }
    else {
      this.show = true;
      this.mensaje = "Error al seleccionar el puerto a eliminar";
    }
  }

  abrirModalEliminarPuerto(content: any) {
    this.modalService.open(content, { centered: true });
  }

  /********************************************************************************************************************/

  obtenerConfiguraciones() {
    this.configuracionApiService.obtenerConfiguraciones().subscribe(configuraciones => {
      this.configuraciones = configuraciones;
      if (this.configuraciones.length > 0) {
        this.configuracion = this.configuraciones[0].nombre;
      }
    });
  }

  eliminarConfiguracion() {
    if (this.configuracion){
      this.configuracionApiService.eliminarConfiguracion(this.configuracion)
        .subscribe({
          next: () => {
            this.obtenerConfiguraciones();
            this.configuracion = undefined;

            this.show = true;
            this.mensaje = "Configuración eliminada con éxito";
          },
          error: (error) => {
            this.show = true;
            this.mensaje = "Error al eliminar la configuración";
          }
        });
    }
    else {
      this.show = true;
      this.mensaje = "Error al seleccionar la configuración a eliminar";
    }
  }

  abrirModalEliminarConfiguracion(content: any) {
    this.modalService.open(content, { centered: true });
  }

  /********************************************************************************************************************/

  subirEscaneoEjemplo() {
    this.escanearService.subirEscaneoEjemplo(this.formData)
      .subscribe({
        next: () => {
          this.obtenerEscaneosEjemplo();

          this.show = true;
          this.mensaje = "Escaneo subido con éxito";
        },
        error: (error) => {
          if(error.status == 400) {
            this.show = true;
            this.mensaje = "Error al subir el escaneo";
          }
          else {
            this.show = true;
            this.mensaje = "No se ha podido subir el escaneo";
          }
        }
      });
  }

  getFile(event: Event) {
    const target = event.target as HTMLInputElement;
    if (target.files && target.files[0]){
      var escaneo : File = target.files[0];

      this.formData.set('file', escaneo);

      this.mensaje =
        `Nombre: ${escaneo.name}\n` +
        `Tipo: ${escaneo.type}\n` +
        `Tamaño: ${escaneo.size} bytes\n` +
        `Creado: ${(new Date(escaneo.lastModified)).toString().substring(0, 24)}\n`;
      this.show = true;
    }
    else {
      this.mensaje = 'Por favor, seleccione un archivo.';
      this.show = true;
    }
  }

  obtenerEscaneosEjemplo() {
    this.escanearService.verEscaneosEjempo()
      .subscribe(escaneos => {
        this.escaneosEjemplo = escaneos;
        if (this.escaneosEjemplo.length > 0) {
          this.escaneoEjemplo = this.escaneosEjemplo[0];
        }
      });
  }

  verEscaneoEjemplo() {
    if (this.escaneoEjemplo){
      this.escanearService.verEscaneoEjemplo(this.escaneoEjemplo)
        .subscribe({
          next: (escaneo) => {
            var blob = new Blob([escaneo], { type: 'text/plain' });
            var url = window.URL.createObjectURL(blob);
            window.open(url);
          },
          error: (error) => {
            this.show = true;
            this.mensaje = "Error al ver el escaneo";
          }
        });
    }
    else {
      this.show = true;
      this.mensaje = "Error al seleccionar el escaneo a ver";
    }
  }

  eliminarEscaneoEjemplo() {
    if (this.escaneoEjemplo){
      this.escanearService.eliminarEscaneoEjemplo(this.escaneoEjemplo)
        .subscribe({
          next: () => {
            this.obtenerEscaneosEjemplo();
            this.escaneoEjemplo = undefined;

            this.show = true;
            this.mensaje = "Escaneo eliminado con éxito";
          },
          error: (error) => {
            if(error.status == 400) {
              this.show = true;
              this.mensaje = "No se ha podido eliminar el escaneo";
            }
            else {
              this.show = true;
              this.mensaje = "Error al eliminar el escaneo";
            }
          }
        });
    }
    else {
      this.show = true;
      this.mensaje = "Error al seleccionar el escaneo a eliminar";
    }
  }

  abrirModalEliminarEscaneoEjemplo(content: any) {
    this.modalService.open(content, { centered: true });
  }

  /********************************************************************************************************************/

  obtenerUsuariosNoAdmin() {
    this.usuariosService.obtenerUsuariosNoAdmin()
      .subscribe(usuarios => {
        this.usuarios = usuarios;
        if (this.usuarios.length > 0) {
          this.usuario = this.usuarios[0].usuario;
        }
      });
  }

  hacerAdministrador() {
    if (this.usuario){
      this.usuariosService.hacerAdministrador(new Usuario(0, this.usuario, false))
        .subscribe({
          next: () => {
            this.obtenerUsuariosNoAdmin();
            this.usuario = undefined;

            this.show = true;
            this.mensaje = "Usuario hecho administrador con éxito";
          },
          error: (error) => {
            console.log(error);
            if (error.status == 404) {
              this.show = true;
              this.mensaje = "Usuario no encontrado";
            }
            else {
              this.show = true;
              this.mensaje = "Error al hacer administrador al usuario";
            }
          }
        });
    }
    else {
      this.show = true;
      this.mensaje = "Error al seleccionar el usuario a hacer administrador";
    }
  }

  abrirModalHacerAdministrador(content: any) {
    this.modalService.open(content, { centered: true });
  }

  /********************************************************************************************************************/

}
