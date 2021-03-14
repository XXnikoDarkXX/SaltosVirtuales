package actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Clase objeto que extiende de actor nos servira  para coger items por el mundo
 */
public class Objeto extends Actor {
    /**
     * Sprite del objeto
     */
    protected Sprite sprite;//el sprite
    /**
     * Mundo donde usamos el objeto
     */
    private World mundo; //el mundo
    /**
     * BodyDef del cuerpo
     */
    private BodyDef propiedadesCuerpo;
    /**
     * Cuerpo del objeto
     */
    private Body cuerpo;//el cuerpo
    /**
     * propiedadesFisicas del cuerpo
     */
    private FixtureDef propiedadesFisicaCuerpo;
    /**
     * float para hacer teleport en pos x
     */
    private float cambioX;//Para el teleport posicionX
    /**
     * float para hacer el teleporY
     */
    private float cambioY;//para el telepor posicionY

    /**
     * Booleano para mostrar el objeto
     */
    private boolean mostrar;//si se tiene que mostrar
    /**
     * String nombre del objeto
     */
    private String nombreObjeto;
    /**
     * String nombreMusica
     */
    private String nombreMusica;
    //

    /**
     * Constructor de objeto
     * @param m mundo
     * @param texture textura
     * @param positionX posicion x
     * @param posicionY posicion y
     * @param ancho ancho
     * @param alto alto
     */
    public Objeto(World m,Texture texture,String nombreObjeto,int positionX,int posicionY,float ancho,float alto){
        this.mundo=m;
        this.mostrar=true;
        this.nombreObjeto=nombreObjeto;
        sprite=new Sprite((texture));

        sprite.setBounds(positionX,posicionY,ancho,alto);
        this.propiedadesCuerpo=new BodyDef();//Establecemos las propiedades del cuero;
        propiedadesCuerpo.type=BodyDef.BodyType.StaticBody;
        propiedadesCuerpo.position.set(sprite.getX(),sprite.getY());
        cuerpo=mundo.createBody(propiedadesCuerpo);





        sprite.setOrigin(this.sprite.getWidth()/2,this.sprite.getHeight()/2);
    }


    /**
     * Segundo constructor de objeto para el telepor
     * @param m mundo
     * @param texture textura
     * @param nombreObjeto nombre del objeto
     * @param positionX posicionX del objeto
     * @param posicionY posicionY del objeto
     * @param ancho float ancho del objeto
     * @param alto float alto del juego
     * @param cambioX posicionX donde hacemos teleport
     * @param cambioY posicionY donde hacemos teleport
     */
    public Objeto(World m,Texture texture,String nombreObjeto,int positionX,int posicionY,float ancho,float alto,float cambioX,float cambioY){
        this.mundo=m;
        this.mostrar=true;
        this.nombreObjeto=nombreObjeto;
        sprite=new Sprite((texture));

        this.cambioX=cambioX;
        this.cambioY=cambioY;



        sprite.setBounds(positionX,posicionY,ancho,alto);
        this.propiedadesCuerpo=new BodyDef();//Establecemos las propiedades del cuero;
        propiedadesCuerpo.type=BodyDef.BodyType.StaticBody;
        propiedadesCuerpo.position.set(sprite.getX(),sprite.getY());
        cuerpo=mundo.createBody(propiedadesCuerpo);





        sprite.setOrigin(this.sprite.getWidth()/2,this.sprite.getHeight()/2);
    }

    /**
     * Tercer constructor de Objeto para musica
     * @param m mundo
     * @param texture textura del objeto
     * @param nombreObjeto nombre del objeto
     * @param rutaMusica ruta de la musica
     * @param positionX posicionX del objeto
     * @param posicionY posicionY del ojbeto
     * @param ancho float ancho del objeto
     * @param alto  float alto del objeto
     */
    public Objeto(World m,Texture texture,String nombreObjeto,String rutaMusica,int positionX,int posicionY,float ancho,float alto){
        this.mundo=m;
        this.mostrar=true;
        this.nombreObjeto=nombreObjeto;
        sprite=new Sprite((texture));
        this.nombreMusica=rutaMusica;
        sprite.setBounds(positionX,posicionY,ancho,alto);
        this.propiedadesCuerpo=new BodyDef();//Establecemos las propiedades del cuero;
        propiedadesCuerpo.type=BodyDef.BodyType.StaticBody;
        propiedadesCuerpo.position.set(sprite.getX(),sprite.getY());
        cuerpo=mundo.createBody(propiedadesCuerpo);





        sprite.setOrigin(this.sprite.getWidth()/2,this.sprite.getHeight()/2);
    }

    /**
     * Funcion para dibujor el objeto
     * @param batch donde dibujamos el objeto
     * @param parentAlpha transparencia del objeto
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {

       if (mostrar){
            sprite.setPosition(cuerpo.getPosition().x-sprite.getWidth()/2,cuerpo.getPosition().y-sprite.getHeight()/2);

            this.setPosition(sprite.getX(),sprite.getY());
        sprite.draw(batch);
        }

    }

    public Body getCuerpo() {
        return cuerpo;
    }


    /**
     * Fucnion para obtener posicion x
     * @return la posicion x
     */
    public float getX(){
        return this.cuerpo.getPosition().x;
    }

    /**
     * Funcion que nos devuelve la posicion y
     * @return la posicion y del cuerpo
     */
    public float getY(){
        return this.cuerpo.getPosition().y;
    }

    /**
     * Getter de mostrar
     * @return true para mostrar false para no mostrar
     */
    public boolean isMostrar() {
        return mostrar;
    }

    /**
     * Setter de mostrar
     * @param mostrar a cambiar
     */
    public void setMostrar(boolean mostrar) {
        this.mostrar = mostrar;
    }

    /**
     * Getter del sprite del objeto
     * @return el sprite del objeto
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Getteer nombre del Objeto
     * @return el nombre del objeto
     */
    public String getNombreObjeto() {
        return nombreObjeto;
    }


    /**
     * Getter de telepor x
     * @return el teleport x
     */
    public float getCambioX() {
        return cambioX;
    }

    /**
     * Getter de telepor y
     * @return el telepor y
     */
    public float getCambioY() {
        return cambioY;
    }

    /**
     * Getter de nombre de musica
     * @return String el nombre de musica
     */
    public String getNombreMusica() {
        return nombreMusica;
    }
}
