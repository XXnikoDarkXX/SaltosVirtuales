package es.com.saltosvirtuales;

import com.badlogic.gdx.Game;


import Pantallas.PantallaJuego;
import Pantallas.PantallaMenu;
import basededatos.BaseDeDatos;

public class Principal extends Game {

	private PantallaJuego pantallaJuego;

	private BaseDeDatos bd;


	public Principal(BaseDeDatos bd){
		this.bd=bd;

	}

	@Override
	public void create() {
		setScreen(new PantallaMenu(this));
	}

	public PantallaJuego getPantallaJuego() {
		this.pantallaJuego=new PantallaJuego(this);
		return pantallaJuego;
	}

	public BaseDeDatos getBd() {
		return bd;
	}
}




