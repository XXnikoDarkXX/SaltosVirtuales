package com.saltosvirtuales.Entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.saltosvirtuales.Constantes;

public class ActorJugador extends Actor {
    private Texture texture;

    private World world;
    private Body body;

    private Fixture fixture;

    private boolean alive = true;

    private boolean jumping = false;

    private boolean mustJump = false;

    public ActorJugador(World world, Texture texture, Vector2 position) {
        this.world = world;
        this.texture = texture;

        // Create the player body.
        BodyDef def = new BodyDef();                // (1) Create the body definition.
        def.position.set(position);                 // (2) Put the body in the initial position.
        def.type = BodyDef.BodyType.DynamicBody;    // (3) Remember to make it dynamic.
        body = world.createBody(def);               // (4) Now create the body.

        // Give it some shape.
        PolygonShape box = new PolygonShape();      // (1) Create the shape.
        box.setAsBox(0.5f, 0.5f);                   // (2) 1x1 meter box.
        fixture = body.createFixture(box, 3);       // (3) Create the fixture.
        fixture.setUserData("player");              // (4) Set the user data.
        box.dispose();                              // (5) Destroy the shape.

        // Set the size to a value that is big enough to be rendered on the screen.
        setSize(Constantes.PIXELS_IN_METERS, Constantes.PIXELS_IN_METERS);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Always update the position of the actor when you are going to draw it, so that the
        // position of the actor on the screen is as accurate as possible to the current position
        // of the Box2D body.
        setPosition((body.getPosition().x - 0.5f) * Constantes.PIXELS_IN_METERS,
                (body.getPosition().y - 0.5f) * Constantes.PIXELS_IN_METERS);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Fucnion para eliminar el body y fixture
     */
    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    @Override
    public void act(float delta) {
        //Iniciar un salto si hemos tocado la pantalla
        if (mustJump) {
            mustJump = false;
            jump();

        }
        if (alive) {
            float rapidezY = body.getLinearVelocity().y;
            body.setLinearVelocity(Constantes.PLAYER_SPEED, rapidezY);
        }

        if (jumping) {
            body.applyForceToCenter(0, -Constantes.IMPULSE_JUMP * 1.15f, true);
        }
    }

    public void jump() {
        if (!jumping && alive) {
            jumping = true;
            Vector2 position = body.getPosition();
            body.applyLinearImpulse(0, Constantes.IMPULSE_JUMP, position.x, position.y, true);
        }

    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void setMustJump(boolean mustJump) {
        this.mustJump = mustJump;
    }

    public boolean isMustJump() {
        return mustJump;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

}