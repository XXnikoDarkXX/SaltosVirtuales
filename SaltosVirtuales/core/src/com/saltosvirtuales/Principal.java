package com.saltosvirtuales;


import com.badlogic.gdx.Game;

public class Principal extends Game {

	@Override
	public void create() {
		setScreen(new Box2DScreen(this));



	}



}
