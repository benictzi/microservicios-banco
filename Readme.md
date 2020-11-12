# GFT Bank


# Componentes

  - Servidor de configuración 
  - Microservicio de gestion de cuentas
  - Microservicio de gestion de clientes
  - Servidor Eureka
  - Servidor Zuul

### Importante!
Asegurese de tener instalado Java SE 8, Gradle 6.6.1 y Docker
 - [JDK 8](https://www.oracle.com/mx/java/technologies/javase/javase-jdk8-downloads.html) 
 - [Gradle 6.6.1](https://docs.gradle.org/current/userguide/installation.html)
 - [Docker](https://www.docker.com/get-started)

### Instalación

Clonar el repositorio y descargar dependencias.

```sh
$ git clone https://benictzi@bitbucket.org/benictzi/microservicios-banco.git

```

Para compilar todos los proyectos en windows solo ejecute el siguiente comando dentro de la carpeta ./microservicios-banco

```sh
$ compilar-servicios.bat
```

Para compilar todos los proyectos en una distribución linux solo ejecute el siguiente comando dentro de la carpeta /microservicios-banco

```sh
$ compilar-servicios.sh
```

Para ejecutar toda la aplicación ingrese dentro de la carpeta /microservicios-banco y ejecute el comando:

```sh
$ docker-compose up
```

Swagger de la aplicación: 
http://localhost:9027/gft-bank/swagger-ui.html#/
