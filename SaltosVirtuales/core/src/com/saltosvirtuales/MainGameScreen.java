package com.saltosvirtuales;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.saltosvirtuales.Pantallas.BaseScreen;
import com.saltosvirtuales.Entidades.ActorJugador;
import com.saltosvirtuales.Entidades.ActorPinchos;

public class MainGameScreen extends BaseScreen {

    public MainGameScreen(Principal game) {
        super(game);
        texturaJugador=new Texture("texturas/player.png");
        texturaPinchos=new Texture("texturas/spike2.png");

    }
    private Stage stage;
    private ActorJugador jugador;
    private ActorPinchos pinchos;
    private Texture texturaJugador, texturaPinchos;



    @Override
    public void show() {

        stage=new Stage();

        stage.setDebugAll(true);
  //      jugador=new ActorJugador(texturaJugador);
      //  pinchos=new ActorPinchos(texturaPinchos);
        stage.addActor(jugador);
        stage.addActor(pinchos);

        jugador.setPosition(20,100);
        pinchos.setPosition(500,100);
    }

    @Override
    public void hide() {
        stage.dispose();//Nos deshacemos del stage al irnos a otra pantalla para no tenerlos en memoria
        texturaJugador.dispose();//liberamos la textura del jugador
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        ///comprobarColisiones();
        stage.draw();
    }
  //  private void comprobarColisiones(){
     //   if (jugador.isVivo()&&(jugador.getX()+jugador.getWidth())>pinchos.getX()){
     ///       System.out.println("Colision");
       //     jugador.setVivo(false);
       // }
    ///}

    @Override
    public void dispose() {
        texturaJugador.dispose();
    }
}
