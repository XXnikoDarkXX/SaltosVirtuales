package com.saltosvirtuales.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.saltosvirtuales.Constantes;
import com.saltosvirtuales.Entidades.ActorPinchos;
import com.saltosvirtuales.Entidades.Floor;
import com.saltosvirtuales.Principal;
import com.saltosvirtuales.Entidades.ActorJugador;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends BaseScreen{

    private Stage stage;
    private World world;
    private ActorJugador jugador;
    private List<Floor> floorList=new ArrayList<Floor>();
    private List<ActorPinchos>spikeList=new ArrayList<ActorPinchos>();
    private Sound jumpSound, dieSound;
    private Music music;

    public GameScreen(final Principal game) {
        super(game);
        jumpSound=game.getManager().get("audio/jump.ogg");
        dieSound=game.getManager().get("audio/die.ogg");
        music=game.getManager().get("audio/song.ogg");
        stage=new Stage(new FillViewport(640,360));
        world=new World(new Vector2(0,-10),true);
        world.setContactListener(new ContactListener() {
            private boolean hanColisionado(Contact contact,Object userA, Object userB){
                return contact.getFixtureA().getUserData().equals(userA)&&contact.getFixtureB().getUserData().equals(userB)||
                        contact.getFixtureA().equals(userB)&&contact.getFixtureB().equals(userA);
            }
            @Override
            public void beginContact(Contact contact) {
                if (hanColisionado(contact,"player","floor")){
                    jugador.setJumping(false);
                    if (Gdx.input.justTouched()){
                        jumpSound.play();
                        jugador.setMustJump(true);
                    }


                }
                if (hanColisionado(contact,"player","spike")){
                    if (jugador.isAlive()) {
                        jugador.setAlive(false);
                        dieSound.play();
                        music.stop();
                        stage.addAction(Actions.sequence(
                                Actions.delay(1.5f),Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        game.setScreen(game.getGameOverScreen());
                                    }
                                })
                        ));
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

    }

    @Override
    public void show() {
        Texture jugadorTextura=game.getManager().get("texturas/player.png");
        Texture floorTexture=game.getManager().get("texturas/floor.png");
        Texture overfloorfexture=game.getManager().get("texturas/overfloor.png");
        Texture spikeTexture=game.getManager().get("texturas/spike.png");
        jugador=new ActorJugador(world,jugadorTextura,new Vector2(1.5f,1.5f));

        floorList.add(new Floor(world,floorTexture,overfloorfexture,0,1000,1));
        floorList.add(new Floor(world,floorTexture,overfloorfexture,13,10,2));
        floorList.add(new Floor(world,floorTexture,overfloorfexture,30,10,2));
        spikeList.add(new ActorPinchos(world,spikeTexture,7,1));
        spikeList.add(new ActorPinchos(world,spikeTexture,18,2));
        spikeList.add(new ActorPinchos(world,spikeTexture,35,2));
        spikeList.add(new ActorPinchos(world,spikeTexture,50,1));
        stage.addActor(jugador);
        for (Floor floor:floorList){
            stage.addActor(floor);
        }
        for (ActorPinchos spike: spikeList){
            stage.addActor(spike);
        }

        music.setVolume(0.75f);
        music.play();
    }

    @Override
    public void hide() {
        music.stop();
        jugador.detach();
        jugador.remove();
        for (Floor floor:floorList){
            floor.detach();
            floor.remove();
        }
        for (ActorPinchos spike: spikeList){
            spike.detach();
            spike.remove();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (jugador.getX()>150&&jugador.isAlive()) {
            stage.getCamera().translate(Constantes.PLAYER_SPEED * delta * Constantes.PIXELS_IN_METERS, 0, 0);
        }

        if (Gdx.input.justTouched()){
            jumpSound.play();
            jugador.jump();
        }
        stage.act();
        world.step(delta,6,2);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}
