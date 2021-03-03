package actores;

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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;
import java.util.HashSet;

import Otros.Constantes;

public class ActorJugador extends Actor {

    protected Sprite sprite;//el sprite
    private World mundo; //el mundo
    private BodyDef propiedadesCuerpo;
    private Body cuerpo;//el cuerpo
    private FixtureDef propiedadesFisicaCuerpo;



    //
    private Fixture fixture;
    private HashSet<Movimiento> movimientosActivos;
    private ArrayList<Objeto>objetos;
    //

    private boolean saltando,masSalto;
    private boolean estaEnElSuelo;
    private boolean vivo;
    private boolean inmortalidad;//variable para controlar la inmortalidad
    private byte puntuacion;

    public  ActorJugador(World m, final ArrayList<Pincho> pincho, final ArrayList<Body>suelos){
      //  addListener(new Teclado(this));


        puntuacion=0;
        movimientosActivos=new HashSet<Movimiento>();

        this.mundo=m;
        this.vivo=true;
        sprite=new Sprite(new Texture("texturas/player.png"));

        sprite.setBounds(5,6.5f,7,6);
        this.setBounds(5,6.5f,7,6);
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



        //
        sprite.setOrigin(this.sprite.getWidth()/2,this.sprite.getHeight()/2);

        propiedadesFisicaCuerpo.shape.dispose();

        mundo.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Body a=contact.getFixtureA().getBody();
                Body b=contact.getFixtureB().getBody();

                //Si hemos colisionado entonces muero
              //  if (pincho.getCuerpo()==a&&b==cuerpo){

                //Buscamos si en algunos de los pinchos hemos colisionado
                for (int i = 0; i < pincho.size(); ++i) {
                    if (pincho.get(i).getCuerpo()==a&&b==cuerpo){
                        //controlamos si tenemos la inmortalidad

                            vivo = false;
                            System.out.println("mori cierto");

                        }
                    }


                if (suelos.contains(a)&&b==cuerpo){
                    saltando=false;
                    estaEnElSuelo=true;
                    System.out.println("contiene suelo");
                   /* if (Gdx.input.justTouched()){
                        System.out.println("toco");
                        masSalto=true;
                    }*/

                }
                //mediante este if comprobamos que estamos en el suelo y podemos saltar al pulsar la tecla a
                if (suelos.contains(b)&&a==cuerpo){
                    saltando=false;
                    estaEnElSuelo=true;
                    System.out.println("contiene suelo");

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

        sprite.setPosition(cuerpo.getPosition().x-sprite.getWidth()/2,cuerpo.getPosition().y-sprite.getHeight()/2);
     //   sprite.setRotation(cuerpo.getAngle());
        //
        sprite.setRotation(this.getRotation());

        //
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
            cuerpo.applyForceToCenter(0,-Constantes.IMPULSE_JUMP * 1.15f,true);
            System.out.println("en el salto estoy");
            //TODO no me rota
    /*
            RotateByAction rotarDcha=new RotateByAction();

            rotarDcha.setDuration(0.3f);
            sprite.setRotation(-13);
            this.addAction(rotarDcha);
*/
        }



    }


    public void salto(){
        if (!saltando&&vivo){
            estaEnElSuelo=false;
            saltando=true;
            Vector2 position=cuerpo.getPosition();
            cuerpo.applyLinearImpulse(0,2000,position.x,position.y,true);


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


    public Sprite getSprite() {
        return sprite;
    }

    public byte getPuntuacion() {
        return puntuacion;
    }

    public Body getCuerpo() {
        return cuerpo;
    }

    public World getMundo() {
        return mundo;
    }

    public boolean isInmortalidad() {
        return inmortalidad;
    }

    public void setPuntuacion(byte puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setSaltando(boolean saltando) {
        this.saltando = saltando;
    }


    public void setInmortalidad(boolean inmortalidad) {
        this.inmortalidad = inmortalidad;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public void setEstaEnElSuelo(boolean estaEnElSuelo) {
        this.estaEnElSuelo = estaEnElSuelo;
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


    public void limpiarMovimiento(){

        this.movimientosActivos=new HashSet<Movimiento>();
    }
    private void mover() {
        if (movimientosActivos.contains(Movimiento.SALTO)) {


            this.setMasSalto(true);

            this.salto();
        }
    }




    public void añadirObjeto(ArrayList<Objeto>objetos){
        this.objetos=objetos;
    }


}
