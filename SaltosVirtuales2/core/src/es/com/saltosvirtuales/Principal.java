package es.com.saltosvirtuales;

import com.badlogic.gdx.Game;


import Pantallas.PantallaJuego;
import Pantallas.PantallaMenu;
import basededatos.BaseDeDatos;

/**
 * Clase principal donde inizializamos el juego y el menu
 */
public class Principal extends Game {
	/**
	 * Variable privada pantalla del juego
	 */
	private PantallaJuego pantallaJuego;
	/**
	 * Base de datos del juego
	 */
	private BaseDeDatos bd;

	/**
	 * Constructor de Principal
	 * @param bd base de datos
	 */
	public Principal(BaseDeDatos bd){
		this.bd=bd;

	}

	@Override
	public void create() {
		setScreen(new PantallaMenu(this));
	}

	/**
	 * Getter de pantalla de juego
	 * @return la PantallaJuego
	 */
	public PantallaJuego getPantallaJuego() {
		this.pantallaJuego=new PantallaJuego(this);
		return pantallaJuego;
	}

	/**
	 * Getter de baseDatos
	 * @return la base datos
	 */
	public BaseDeDatos getBd() {
		return bd;
	}
}




