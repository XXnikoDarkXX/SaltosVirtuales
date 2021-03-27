# SaltosVirtuales
Proyecto de Juego creado en LibGDX para 2º Trimestre de PDM 2020-2021
Este juego se base en un Geometry Dash

## Instrucciones 🚀

Descargar, clonar, este repositorio y abrir con Android Studio la carpeta SaltosVirtuales2

En caso de no ejecutar directamente la version desktop tendreis que crear una nueva configuracion para poder ejecutar el juego en modo escritorio


### Pre-requisitos 
####Base de Datos

create database geometry;

use geometry;

create table partida(
	puntucion int(2),
    muertes int (3));
    
    insert into partida values(0,0);
    update partida set puntucion=0;
    select * from partida;

## Construido con 🛠️

* [LibGDX](https://libgdx.com/) - El motor de juego usado
* [Android Studio](https://developer.android.com/studio?hl=es) - IDE para creacion del proyecto
* [Trello](https://trello.com/) - Organizador de tareas por medio de tablones
* [Trello](https://trello.com/) - Organizador de tareas por medio de tablones

 
 
 

## Licencia 📄

Este proyecto está bajo la Licencias GPL
