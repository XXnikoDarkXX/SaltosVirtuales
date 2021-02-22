package com.saltosvirtuales.actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorPinchos extends Actor {

    private Texture pinchos;

    @Override
    public void act(float delta) {
        setX(getX()-250*delta);
    }

    public ActorPinchos(Texture pinchos){

        this.pinchos=pinchos;

        setSize(pinchos.getWidth(),pinchos.getHeight());
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
       batch.draw(pinchos,getX(),getY());
    }
}
