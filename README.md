# PRUEBA TECNICA JAVA MICROSERVICIOS DEVSU


## Se desarrollaron 3 microservicios utilizando Java con JDK 17.0.2 y SpringBoot 3.3.1 y Base de Datos MySQL

## Todos los microservicios son:

    /eureka-server
    /cuenta-movimientos-service
    /cliente-persona-service

## Eureka Server es el gestor de Microservicios por lo cual debe levantarse primero al igual que RabbitMQ y MySQL ya que los otros microservcios dependen de todas estas tecnologias y por ultimo /cuenta-movimientos-service    /cliente-persona-service

## Se utiliza Maven para la gestion de proyectos

## Se utiliza principios SOLID, MCV orientado a API RESTFULL

## Pruebas unitarias Junit5 y Mockito

## Pruebas de Integracion

## Funciones Asincronas y funciones del API Stream de Java


## cada microservicio contiene un Dockerfile para la generacion de la imagen

## se tiene el archivo docker-compose.yml para levantar todas las imagenes y contenedores Docker

   se tienen 3 ramas para cada Microservicio en el presente Proyecto
