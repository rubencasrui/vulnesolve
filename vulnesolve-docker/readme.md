# VulneSolve Docker

## Estructura del proyecto

```
project/
│
├── mysql/
│   ├── Dockerfile
│   └── mysql-init.sql
│
├── spring-boot/
│   ├── Dockerfile
│   └── vulnesolve-0.0.1.jar
│
└── docker-compose.yml
```

## Descargar el proyecto

```bash
git clone
```

## Actualizar el proyecto

```bash
git pull
```

## Ejecutar el proyecto

```bash
cd project

docker compose up -d
```

## Acceder a la aplicación

[http://vulnesolve.ddns.net:8080](http://vulnesolve.ddns.net:8080)

## Detener el proyecto

```bash
cd project

docker compose down
```
