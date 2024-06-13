export class ConfiguracionApi {

  id : number;
  nombre : string;
  cantidadResultados : number;
  modoBusqueda : number;
  soloCriticos : boolean;
  incrementoIndice : number;

  constructor(id: number, nombre: string, cantidadResultados: number, modoBusqueda: number, soloCriticos: boolean, incrementoIndice: number) {
    this.id = id;
    this.nombre = nombre;
    this.cantidadResultados = cantidadResultados;
    this.modoBusqueda = modoBusqueda;
    this.soloCriticos = soloCriticos;
    this.incrementoIndice = incrementoIndice;
  }

}
