package es.com.saltosvirtuales;

import com.badlogic.gdx.Game;


import Pantallas.PantallaJuego;
import Pantallas.PantallaMenu;
import basededatos.BaseDatos;

/**
 * Clase principal donde inizializamos el juego y el menu
 */
public class Principal extends Game {
	/**
	 * Variable privada pantalla del juego
	 */
	private PantallaJuego pantallaJuego;
	private BaseDatos bd;
	/**
	 * Base de datos del juego
	 */


	/**
	 * Constructor de Principal
	 * @param
	 */
	public Principal(BaseDatos bd){

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
		this.pantallaJuego=new PantallaJuego(this,this.bd);
		return pantallaJuego;
	}


}




