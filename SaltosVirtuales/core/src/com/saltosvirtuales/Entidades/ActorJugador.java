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

    /**
     * The player texture.
     */
    private Texture texture;

    /**
     * The world instance this player is in.
     */
    private World world;

    /**
     * The body for this player.
     */
    private Body body;

    /**
     * The fixture for this player.
     */
    private Fixture fixture;

    /**
     * Is the player alive? If he touches a spike, is not alive. The player will only move and
     * jump if it's alive. Otherwise it is said that the user has lost and the game is over.
     */
    private boolean alive = true;

    /**
     * Is the player jumping? If the player is jumping, then it is not possible to jump again
     * because the user cannot double jump. The flag has to be set when starting a jump and be
     * unset when touching the floor again.
     */
    private boolean jumping = false;

    /**
     * Does the player have to jump? This flag is used when the player touches the floor and the
     * user is still touching the screen, to make a double jump. Remember that we cannot add
     * a force inside a ContactListener. We have to use this flag to remember that the player
     * had to jump after the collision.
     */
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
        if ( mustJump) {
            mustJump = false;
            jump();

        }
        if (alive){
            float rapidezY=body.getLinearVelocity().y;
            body.setLinearVelocity(Constantes.PLAYER_SPEED,rapidezY);
        }

        if (jumping){
        body.applyForceToCenter(0,-Constantes.IMPULSE_JUMP*1.15f,true);
        }
    }

    public void jump() {
        if (!jumping&&alive) {
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

