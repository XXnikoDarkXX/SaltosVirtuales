package es.com.saltosvirtuales;

import com.badlogic.gdx.Game;


import Pantallas.PantallaJuego;
import Pantallas.PantallaMenu;

public class Principal extends Game {

	private PantallaJuego pantallaJuego;

	private PantallaMenu pantallaMenu;

	@Override
	public void create() {
		setScreen(new PantallaMenu(this));
	}

	public PantallaJuego getPantallaJuego() {
		this.pantallaJuego=new PantallaJuego(this);
		return pantallaJuego;
	}


}




