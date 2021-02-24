package com.saltosvirtuales;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.saltosvirtuales.Pantallas.BaseScreen;

public class Box2DScreen extends BaseScreen {

    public Box2DScreen(Principal game) {
        super(game);
    }

    private World world;
    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;
    private Body playerBody, sueloBody, pinchoBody;
    private Fixture playerFixture, sueloFixture,pinchoFixture;

    private boolean debeSaltar,playerSaltando;
    private boolean playerVivo=true;

    @Override
    public void show() {
       world=new World(new Vector2(0,-10),true);
       renderer=new Box2DDebugRenderer();
       camera=new OrthographicCamera(16,9);
       camera.translate(0,1);


       //escuchar cuando se produce esso contactos
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA=contact.getFixtureA(),fixtureB=contact.getFixtureB();
                if (fixtureA.getUserData().equals("player")&&fixtureB.getUserData().equals("floor")||(fixtureA.getUserData().equals("floor")
                &&fixtureB.getUserData().equals("player"))){
                    if (Gdx.input.justTouched()){
                        debeSaltar=true;
                    }
                    playerSaltando=false;
                }

                if (fixtureA.getUserData().equals("player")&&fixtureB.getUserData().equals("spike")||(fixtureA.getUserData().equals("spike")
                        &&fixtureB.getUserData().equals("player"))){

                       playerVivo=false;


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

      sueloBody=world.createBody(createSueloBodyDef());
      playerBody=world.createBody(createPlayerBodyDef());
      pinchoBody=world.createBody(createPinchoBodyDef(7));

        PolygonShape playerShape=new PolygonShape();
        playerShape.setAsBox(0.5f,0.45f);
        playerFixture=playerBody.createFixture(playerShape,3);
        playerShape.dispose();

        PolygonShape sueloShape=new PolygonShape();
        sueloShape.setAsBox(500,1);
        sueloFixture=sueloBody.createFixture(sueloShape,1);
        sueloShape.dispose();

        pinchoFixture =createPinchoFixture(pinchoBody);

        playerFixture.setUserData("player");
        sueloFixture.setUserData("floor");
        pinchoFixture.setUserData("spike");
    }

    private BodyDef createPlayerBodyDef(){
        BodyDef def=new BodyDef();
        def.position.set(0,0.5f);
        def.type=BodyDef.BodyType.DynamicBody;
        return def;
    }


    private BodyDef createPinchoBodyDef(float x){
        BodyDef def=new BodyDef();
        def.position.set(x,0.5f);
        return def;
    }
    private BodyDef createSueloBodyDef(){
        BodyDef def=new BodyDef();
        def.position.set(0,-1);

        return def;
    }

    private Fixture createPinchoFixture(Body pinchoBody){
        Vector2[] vertices =new Vector2[3];
        PolygonShape shape=new PolygonShape();
        vertices[0]=new Vector2(-0.5f,-0.5f);
        vertices[1]=new Vector2(0.5f,-0.5f);
        vertices[2]=new Vector2(0,0.5f);
        shape.set(vertices);
        Fixture fix=pinchoBody.createFixture(shape,1);
        shape.dispose();
        return fix;

    }

    @Override
    public void dispose() {
        world.destroyBody(playerBody);
        playerBody.destroyFixture(playerFixture);
        sueloBody.destroyFixture(sueloFixture);
        pinchoBody.destroyFixture(pinchoFixture);
        world.destroyBody(playerBody);
        world.destroyBody(pinchoBody);
        world.destroyBody(sueloBody);
        world.dispose();


        renderer.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (debeSaltar){
            debeSaltar =false;
            saltar();
        }

        if (Gdx.input.justTouched()&&!playerSaltando) {
            debeSaltar=true;
        }
        if (playerVivo) {
            float velocidadY = playerBody.getLinearVelocity().y;
            playerBody.setLinearVelocity(8, velocidadY);
        }
        world.step(delta, 6,2);

        camera.update();
        renderer.render(world,camera.combined);
    }
    private void saltar(){
       Vector2 position= playerBody.getPosition();
        playerBody.applyLinearImpulse(0,6, position.x,position.y,true);
    }
}
