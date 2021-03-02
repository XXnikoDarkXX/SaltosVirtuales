package actores;

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

public class Objeto extends Actor {
    protected Sprite sprite;//el sprite
    private World mundo; //el mundo
    private BodyDef propiedadesCuerpo;
    private Body cuerpo;//el cuerpo
    private FixtureDef propiedadesFisicaCuerpo;

    //
    private boolean mostrar;//si se tiene que mostrar
    private String nombreObjeto;
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
    public Objeto(World m,Texture texture,String nombreObjeto,int positionX,int posicionY,int ancho,int alto){
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
     * @return
     */
    public float getY(){
        return this.cuerpo.getPosition().y;
    }

    public boolean isMostrar() {
        return mostrar;
    }

    public void setMostrar(boolean mostrar) {
        this.mostrar = mostrar;
    }


    public Sprite getSprite() {
        return sprite;
    }


    public String getNombreObjeto() {
        return nombreObjeto;
    }

    public void setNombreObjeto(String nombreObjeto) {
        this.nombreObjeto = nombreObjeto;
    }


}
