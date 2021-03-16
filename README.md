# SaltosVirtuales

BBDD
create database geometry;

use geometry;

create table partida(
	puntucion int(2),
    muertes int (3));
    
    insert into partida values(0,0);
    update partida set puntucion=0;
    select * from partida;
