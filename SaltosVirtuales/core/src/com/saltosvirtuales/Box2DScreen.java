package com.saltosvirtuales;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DScreen extends BaseScreen{

    public Box2DScreen(Principal game) {
        super(game);
    }

    private World world;
    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;
    private Body playerBody, sueloBody;
    private Fixture playerFixture, sueloFixture;


    @Override
    public void show() {
       world=new World(new Vector2(0,-10),true);
       renderer=new Box2DDebugRenderer();
       camera=new OrthographicCamera(7.11f,4);
       camera.translate(0,1);
      sueloBody=world.createBody(createSueloBodyDef());
      playerBody=world.createBody(createPlayerBodyDef());
        PolygonShape playerShape=new PolygonShape();
        playerShape.setAsBox(0.5f,0.4f);
        playerFixture=playerBody.createFixture(playerShape,1);
        playerShape.dispose();

        PolygonShape sueloShape=new PolygonShape();
        sueloShape.setAsBox(500,1);
        sueloFixture=sueloBody.createFixture(sueloShape,1);
        sueloShape.dispose();


    }

    private BodyDef createPlayerBodyDef(){
        BodyDef def=new BodyDef();
        def.position.set(0,10);
        def.type=BodyDef.BodyType.DynamicBody;
        return def;
    }
    private BodyDef createSueloBodyDef(){
        BodyDef def=new BodyDef();
        def.position.set(0,-1);

        return def;
    }

    @Override
    public void dispose() {
        world.destroyBody(playerBody);
        world.dispose();
        super.dispose();
        renderer.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(delta, 6,2);

        camera.update();
        renderer.render(world,camera.combined);
    }
}
