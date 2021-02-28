package com.saltosvirtuales.Entidades;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class EntityFactory {

    private AssetManager manager;


    public EntityFactory(AssetManager manager) {
        this.manager = manager;
    }


    public ActorJugador createPlayer(World world, Vector2 position) {
        Texture playerTexture = manager.get("texturas/player.png");
        return new ActorJugador(world, playerTexture, position);
    }


    public Floor createFloor(World world, float x, float width, float y) {
        Texture floorTexture = manager.get("texturas/floor.png");
        Texture overfloorTexture = manager.get("texturas/overfloor.png");
        return new Floor(world, floorTexture, overfloorTexture, x, width, y);
    }


    public ActorPinchos createSpikes(World world, float x, float y) {
        Texture spikeTexture = manager.get("texturas/spike.png");
        return new ActorPinchos(world, spikeTexture, x, y);
    }
}
