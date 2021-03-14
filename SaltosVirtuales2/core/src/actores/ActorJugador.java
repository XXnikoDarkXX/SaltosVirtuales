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
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

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
            /**
             * Funcion para hacer acciones cuando colisionan con otros body
             * @param contact body con el que colisionamos
             */
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

    /**
     * Funcion para dibujar los sprite
     * @param batch batch que usamos para dibujar
     * @param parentAlpha transparencia del sprite
     */
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
    public void seguir(OrthographicCamera camara,long momento){


                if(System.currentTimeMillis()-momento<1500) {
                    camara.position.x = this.cuerpo.getPosition().x;
                    camara.position.y = this.cuerpo.getPosition().y;
                }
           sprite.setPosition(this.cuerpo.getPosition().x,this.cuerpo.getPosition().y);
    }

    /**
     * Funcion act que es donde metemos las acciones del jugador
     * @param delta los frames
     */
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

    /**
     * Funcion para saltar
     */
    public void salto(){
        if (!saltando&&vivo){
            estaEnElSuelo=false;
            saltando=true;
            Vector2 position=cuerpo.getPosition();
            cuerpo.applyLinearImpulse(0,50,position.x,position.y,true);


        }
    }


    /**
     * Getter de si esta en el suelo
     * @return true si esta en el suelo false si no lo esta
     */
    public boolean isEstaEnElSuelo() {
        return estaEnElSuelo;
    }


    /**
     * Getter de vivo
     * @return true si esta vi
     */
    public boolean isVivo() {
        return vivo;
    }

    /**
     * Getter de parar Tiempo
     * @return true podemos parar tiempo false no podemos parar tiempo
     */
    public boolean isPararTiempo() {
        return pararTiempo;
    }

    /**
     * Setter de parar tiempo
     * @param pararTiempo a cambiar
     */
    public void setPararTiempo(boolean pararTiempo) {
        this.pararTiempo = pararTiempo;
    }


    /**
     * Getter del sprite del jugador
     * @return el sprite del jugador
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Getter de puntuacion
     * @return la puntuacion en byte
     */
    public byte getPuntuacion() {
        return puntuacion;
    }

    /**
     * Getter de body del cuerpo
     * @return el cuerop del jugador
     */
    public Body getCuerpo() {
        return cuerpo;
    }

    /**
     * Getter del body del pincho que colisionamos
     * @return el body del pincho que colisionamos
     */
    public Body getPinchoDestruido() {
        return pinchoDestruido;
    }

    /**
     * Setter de pincho
     * @param pinchoDestruido a cambiar
     */
    public void setPinchoDestruido(Body pinchoDestruido) {
        this.pinchoDestruido = pinchoDestruido;
    }


    /**
     * Getter de ganar
     * @return true si hemos ganado false si todavia no
     */
    public boolean isGanar() {
        return ganar;
    }

    /**
     * Setter de puntuacion
     * @param puntuacion a cambiar
     */
    public void setPuntuacion(byte puntuacion) {
        this.puntuacion = puntuacion;
    }


    /**
     * Setter de inmortalidad
     * @param inmortalidad a cambiar
     */
    public void setInmortalidad(boolean inmortalidad) {
        this.inmortalidad = inmortalidad;
    }

    /**
     * Setter de vivo
     * @param vivo a cambiar
     */
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }


    /**
     * Setter de masAlto
     * @param masSalto a cambiar
     */
    public void setMasSalto(boolean masSalto) {
        this.masSalto = masSalto;
    }

    /**
     * Funcion para finalizar un movimiento lo que hacemos es remover el movimiento usado de la coleccion
     * @param p Enumerado Movimiento que pasamos por contructor para luego borrarlo de la coleccion interna
     */
    public void finalizarMovimiento(Movimiento p){
        this.movimientosActivos.remove(p);
    }

    /**
     * Funcion para inicar el movimiento añadiendo a la coleccion de movimiento el movimiento pasado por parametro
     * @param p Enumerado de tipo Movimiento
     */
    public void iniciarMovimiento(Movimiento p){

        this.movimientosActivos.add(p);
    }


    /**
     * Funcion para hacer la accion del jugador
     * Buscamos en la coleccion que es lo que contiene y segun que caso parara o saltara
     */
    private void mover() {
        if (movimientosActivos.contains(Movimiento.SALTO)) {


            this.setMasSalto(true);

            this.salto();
        }
        if (movimientosActivos.contains(Movimiento.PARAR)){
            avanzar(5f);

        }
    }


    /**
     * Funcion para que el jugador avance hacia la derecha
     * @param jugadorSpeed float pasamos cuanto speed queremos que avance
     */
    public void avanzar(float jugadorSpeed){
        if (vivo) {
            float rapidezY = this.cuerpo.getLinearVelocity().y;
            cuerpo.setLinearVelocity(Constantes.PLAYER_SPEED-jugadorSpeed, rapidezY);
        }else{

            System.out.println(" x : "+sprite.getX()+" Y "+sprite.getY());
        }
    }


    /**
     * Funcion para devolver el body del pincho al que colisiona
     * @param b Body le pasaremos seguramente el body del pincho
     * @return un body
     */
   public Body devuelvePinchoBody(Body b){
        return b;
   }
}
