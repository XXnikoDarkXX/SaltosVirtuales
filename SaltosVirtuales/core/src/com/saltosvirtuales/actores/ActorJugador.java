package com.saltosvirtuales.actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorJugador extends Actor {
    private Texture jugador;
    private boolean vivo;



    public ActorJugador(Texture jugador){

    this.jugador=jugador;
    this.vivo=true;
    setSize(jugador.getWidth(),jugador.getHeight());

    }
    @Override
    public void act(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
     batch.draw(jugador,getX(),getY());
    }


    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
}

