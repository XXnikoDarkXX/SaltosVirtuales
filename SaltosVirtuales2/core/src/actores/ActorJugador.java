package actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;
import java.util.HashSet;

import Otros.Constantes;

/**
 * @author nicoc
 * Clase Actor Jugador
 * Clase que nos brinda el jugador principal desde aqui manipularemos las colisiones, ademas de sincronizar los movimientos del jugador
 *
 *
 */
public class ActorJugador extends Actor {

    /**
     * Sprite del jugador
     */
    protected Sprite sprite;

    /**
     * mundo del jugador
     */
    private World mundo; //el mundo
    /**
     * BodyDef del jugador
     */
    private BodyDef propiedadesCuerpo;
    /**
     * Body del jugador
     */
    private Body cuerpo;//el cuerpo
    /**
     * FixtureDef del jugador
     */
    private FixtureDef propiedadesFisicaCuerpo;
    /**
     * Fixture del jugador
     */
    private Fixture fixture;
    /**
     * hashSet de Movimiento
     */
    private HashSet<Movimiento> movimientosActivos;
    /**
     * Variable para saber si esta en esta en el salto el jugador
     */
    private boolean saltando;
    /**
     * Variable para controlar los saltos
     */
    private boolean masSalto;
    /**
     * Variable para saber si esta en el suelo
     */
    private boolean estaEnElSuelo;
    /**
     * Variable para saber si esta vivo el jugador
     */
    private boolean vivo;
    /**
     * Variable saber si puede pararTiempo el jugador
     */
    private boolean pararTiempo;
    /**
     * Variable para saber si hemos ganado
     */
    private boolean ganar;
    /**
     * Variable para saber si somos  inmortales
     */
    private boolean inmortalidad;//variable para controlar la inmortalidad
    /**
     * Variable con la puntuacion del jugador
     */
    private byte puntuacion;
    /**
     * Variable para controlar el cuerpo del pincho
     */
    private Body pinchoDestruido;


    /**
     * Constructor por defecto de Actor Jugador
     * @param m mundo del jugador
     * @param pincho Array de pinchos para controlar los choques con el jugador
     * @param suelos suelo donde pisa el jugador
     * @param caida array de caida en caso de que caiga el jugador
     * @param win bloque final del mapa para saber que ha ganado
     */
    public  ActorJugador(World m, final ArrayList<Pincho> pincho, final ArrayList<Body>suelos,final ArrayList<Body>caida,final ArrayList<Body>win){


        puntuacion=0;

        movimientosActivos=new HashSet<Movimiento>();

        this.mundo=m;
        this.vivo=true;
        sprite=new Sprite(new Texture("texturas/player.png"));


        //desde el principio
     sprite.setBounds(5,6.5f,1f,1.5f);
        //Parte 1 hacia arriba
       // sprite.setBounds(90,6.5f,1f,1.5f);
    //parte 1 hacia abajo invierno
       //sprite.setBounds(163,16.8f,1f,1.5f);
        //amarillo
       // sprite.setBounds(220.71465f,   2.755f,1f,1.5f);

      //  Saltando pincho obstaculo
      // sprite.setBounds(245.53915f,   4.754999f,1f,1.5f);
        //Terminamos amarillo
       // sprite.setBounds(315f,   2,1f,1.5f);

 //       sprite.setBounds(353.44974f, 2.0149999f,1f,1.5f);

       //parte rojo  con moneda
    //    sprite.setBounds(430f, 15.3f,1f,1.5f);

        this.propiedadesCuerpo=new BodyDef();//Establecemos las propiedades del cuero;
        propiedadesCuerpo.fixedRotation = true;//Cuerpo no rote
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




       // sprite.setOrigin(this.sprite.getWidth()/2,this.sprite.getHeight()/2);



        mundo.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Body a=contact.getFixtureA().getBody();
                Body b=contact.getFixtureB().getBody();

                //Si hemos colisionado entonces muero
                //Buscamos si en algunos de los pinchos hemos colisionado
                for (int i = 0; i < pincho.size(); ++i) {
                    if (pincho.get(i).getCuerpo()==a&&b==cuerpo){
                        //si somos inmortales destruimos el body del pincho  y asi podremos atraversarlo sin morir
                        if (inmortalidad==true){
                           pinchoDestruido= devuelvePinchoBody(pincho.get(i).getCuerpo());

                        }else {

                            //controlamos si tenemos la inmortalidad

                            vivo = false;
                            System.out.println("mori cierto");
                       }

                    }
                }


                if (suelos.contains(a)&&b==cuerpo){
                    saltando=false;
                    estaEnElSuelo=true;


                }
                //mediante este if comprobamos que estamos en el suelo y podemos saltar al pulsar la tecla a
                if (suelos.contains(b)&&a==cuerpo){
                    saltando=false;
                    estaEnElSuelo=true;
                    System.out.println("contiene suelo");
                    System.out.println(cuerpo.getPosition().x+"   "+cuerpo.getPosition().y);

                }

                if (caida.contains(a)&&b==cuerpo){
                        vivo=false;
                    System.out.println("Game over");

                }

                if (caida.contains(b)&&a==cuerpo){
                    vivo=false;
                    System.out.println("Game over");


                }
                if (win.contains(a)&&b==cuerpo){
                    ganar=true;
                }

                if (win.contains(b)&&a==cuerpo){
                    ganar=true;
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
        mover();

        super.draw(batch, parentAlpha);
        mover();
        sprite.setPosition(cuerpo.getPosition().x-sprite.getWidth()/2,
                cuerpo.getPosition().y-sprite.getHeight()/2);
        this.setPosition(cuerpo.getPosition().x,
                cuerpo.getPosition().y);
        sprite.setScale(getScaleX(),getScaleY());
        sprite.setRotation(cuerpo.getAngle());
        this.setRotation(cuerpo.getAngle());
        sprite.setColor(getColor().r,getColor().g,getColor().b,getColor().a);
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
    /*   if    (this.pinchoDestruido!=null) {
                mundo.destroyBody(pinchoDestruido);
              pinchoDestruido=null;
              inmortalidad=false;
            }
            */

        //si estamos vivo seguiremos avanzando

        if (vivo==true){
            avanzar(0f);
        }

        if (saltando){
            cuerpo.applyForceToCenter(0,-Constantes.IMPULSE_JUMP * 1.15f,true);

        }



    }


    public void salto(){
        if (!saltando&&vivo){
            estaEnElSuelo=false;
            saltando=true;
            Vector2 position=cuerpo.getPosition();
            cuerpo.applyLinearImpulse(0,50,position.x,position.y,true);


        }
    }



    public boolean isEstaEnElSuelo() {
        return estaEnElSuelo;
    }

    public boolean isSaltando() {
        return saltando;
    }

    public boolean isVivo() {
        return vivo;
    }

    public boolean isPararTiempo() {
        return pararTiempo;
    }

    public void setPararTiempo(boolean pararTiempo) {
        this.pararTiempo = pararTiempo;
    }



    public Sprite getSprite() {
        return sprite;
    }

    public byte getPuntuacion() {
        return puntuacion;
    }

    public Body getCuerpo() {
        return cuerpo;
    }

    public Body getPinchoDestruido() {
        return pinchoDestruido;
    }

    public void setPinchoDestruido(Body pinchoDestruido) {
        this.pinchoDestruido = pinchoDestruido;
    }

    public boolean isInmortalidad() {
        return inmortalidad;
    }


    public boolean isGanar() {
        return ganar;
    }

    public void setPuntuacion(byte puntuacion) {
        this.puntuacion = puntuacion;
    }



    public void setInmortalidad(boolean inmortalidad) {
        this.inmortalidad = inmortalidad;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }



    public void setMasSalto(boolean masSalto) {
        this.masSalto = masSalto;
    }

    public void finalizarMovimiento(Movimiento p){
        this.movimientosActivos.remove(p);
    }

    public void iniciarMovimiento(Movimiento p){

        this.movimientosActivos.add(p);
    }



    private void mover() {
        if (movimientosActivos.contains(Movimiento.SALTO)) {


            this.setMasSalto(true);

            this.salto();
        }
        if (movimientosActivos.contains(Movimiento.PARAR)){
            avanzar(5f);

        }
    }



    public void avanzar(float jugadorSpeed){
        if (vivo) {
            float rapidezY = this.cuerpo.getLinearVelocity().y;
            cuerpo.setLinearVelocity(Constantes.PLAYER_SPEED-jugadorSpeed, rapidezY);
        }else{

            System.out.println(" x : "+sprite.getX()+" Y "+sprite.getY());
        }
    }





   public Body devuelvePinchoBody(Body b){
        return b;
   }
}
