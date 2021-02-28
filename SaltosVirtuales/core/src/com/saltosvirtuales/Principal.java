package com.saltosvirtuales;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.saltosvirtuales.Pantallas.GameOverScreen;
import com.saltosvirtuales.Pantallas.GameScreen;
import com.saltosvirtuales.Pantallas.LoadingScreen;
import com.saltosvirtuales.Pantallas.MenuScreen;

import javax.swing.ImageIcon;

public class Principal extends Game {
	private GameScreen gameScreen;
	private GameOverScreen gameOverScreen;
	private AssetManager manager;
	private LoadingScreen loadingScreen;
	private MenuScreen menuScreen;
	public AssetManager getManager(){
		return manager;
	}
//---------------------------------------------------------------

	@Override
	public void create() {
		manager=new AssetManager();
		manager.load("texturas/player.png", Texture.class);
		manager.load("texturas/spike.png", Texture.class);
		manager.load("texturas/spike2.png", Texture.class);
		manager.load("texturas/spike3.png", Texture.class);
		manager.load("texturas/overfloor.png",Texture.class);
		manager.load("texturas/floor.png", Texture.class);
		manager.load("audio/die.ogg", Sound.class);
		manager.load("audio/jump.ogg",Sound.class);
		manager.load("audio/song.ogg", Music.class);
		manager.load("texturas/gameover.png",Texture.class);
		manager.load("texturas/logo.png",Texture.class);

		loadingScreen=new LoadingScreen(this);
		setScreen(new Box2DScreen(this));




	}

	public GameScreen getGameScreen() {
		this.gameScreen=new GameScreen(this);
		return gameScreen;
	}

	public GameOverScreen getGameOverScreen() {
		return gameOverScreen;
	}

	public LoadingScreen getLoadingScreen() {
		return loadingScreen;
	}

	public MenuScreen getMenuScreen() {
		return menuScreen;
	}

	public void finishLoading(){
		this.menuScreen=new MenuScreen(this);
		gameScreen=new GameScreen(this);
		gameOverScreen=new GameOverScreen(this);
		setScreen(menuScreen);

	}



}
