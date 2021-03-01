package actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

import Otros.Constantes;

public class ActorJugador extends Actor {

    protected Sprite sprite;//el sprite
    private World mundo; //el mundo
    private BodyDef propiedadesCuerpo;
    private Body cuerpo;//el cuerpo
    private FixtureDef propiedadesFisicaCuerpo;



    ///
    private Fixture fixture;
    //

    private boolean saltando,masSalto;
    private boolean vivo;

    public  ActorJugador(World m, final Pincho pincho, final ArrayList<Body>suelos){
        this.mundo=m;
        this.vivo=true;
        sprite=new Sprite(new Texture("texturas/player.png"));
      //  float anchuraSprite=5f;
       // float alturaSprite=5f;
        sprite.setBounds(5,6.5f,8,7);
        this.setBounds(5,6.5f,8,7);
        this.propiedadesCuerpo=new BodyDef();//Establecemos las propiedades del cuero;
        propiedadesCuerpo.type=BodyDef.BodyType.DynamicBody;
        propiedadesCuerpo.position.set(sprite.getX(),sprite.getY());
        cuerpo=mundo.createBody(propiedadesCuerpo);
        propiedadesFisicaCuerpo=new FixtureDef();
        propiedadesFisicaCuerpo.shape=new PolygonShape();
        ((PolygonShape)propiedadesFisicaCuerpo.shape).setAsBox(sprite.getWidth()/2,sprite.getHeight()/2);//establecemos el box2d
        propiedadesFisicaCuerpo.density=1f;
        cuerpo.createFixture(propiedadesFisicaCuerpo);
        //
        fixture=cuerpo.createFixture(((PolygonShape)propiedadesFisicaCuerpo.shape),3);
        fixture.setUserData("jugador");
        //
        sprite.setOrigin(this.sprite.getWidth()/2,this.sprite.getHeight()/2);


        mundo.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Body a=contact.getFixtureA().getBody();
                Body b=contact.getFixtureB().getBody();

                //Si hemos colisionado entonces muero
                if (pincho.getCuerpo()==a&&b==cuerpo){
                    vivo=false;
                    System.out.println("mori cierto");
                }
                if (suelos.contains(a)&&b==cuerpo){
                    saltando=false;
                    System.out.println("contiene suelo");
                    if (Gdx.input.justTouched()){
                        System.out.println("toco");
                        masSalto=true;
                    }

                }
                if (suelos.contains(b)&&a==cuerpo){
                    saltando=false;
                    System.out.println("contiene suelo");
                    if (Gdx.input.isKeyPressed(Input.Keys.A)){
                        System.out.println("toco");
                        masSalto=true;
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
    public void draw(Batch batch, float parentAlpha) {
       sprite.setPosition(cuerpo.getPosition().x-sprite.getWidth()/2,cuerpo.getPosition().y-sprite.getHeight()/2);
        sprite.setRotation(cuerpo.getAngle());
    this.setPosition(sprite.getX(),sprite.getY());
       sprite.draw(batch);

    }

    /**
     * Funcion para el seguimiento de la camara
     * @param camara la camara
     */
    public void seguir(OrthographicCamera camara){
        camara.position.x=this.cuerpo.getPosition().x;
        camara.position.y=this.cuerpo.getPosition().y;
     //   sprite.setPosition(this.cuerpo.getPosition().x,this.cuerpo.getPosition().y);
    }


    @Override
    public void act(float delta) {
        //Iniciar un salto si hemos tocado la pantalla
        if (masSalto){
            masSalto=false;
            salto();
        }

        //si estamos vivo seguiremos avanzando
        if (vivo) {
            float rapidezY = this.cuerpo.getLinearVelocity().y;
            cuerpo.setLinearVelocity(Constantes.PLAYER_SPEED, rapidezY);
        }

        if (saltando){
            cuerpo.applyForceToCenter(0,-Constantes.IMPULSE_JUMP*1.15f,true);
        }

    }


    public void salto(){
        if (!saltando&&vivo){
            saltando=true;
            Vector2 position=cuerpo.getPosition();
            cuerpo.applyLinearImpulse(0,Constantes.IMPULSE_JUMP,position.x,position.y,true);
        }
    }


    public boolean isSaltando() {
        return saltando;
    }

    public boolean isVivo() {
        return vivo;
    }


    public void setSaltando(boolean saltando) {
        this.saltando = saltando;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
}
