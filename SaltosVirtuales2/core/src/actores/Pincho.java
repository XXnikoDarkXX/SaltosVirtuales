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

/**
 * Clase pincho que sirve para dibujar un pincho con su propiedades fisicas
 * El jugador tendra que saltarlo
 */
public class Pincho extends Actor {
    /**
     * Sprite del pincho
     */
    protected Sprite sprite;//el sprite
    /**
     * Mundo donde se aloja el pincho
     */
    private World mundo; //el mundo
    /**
     * Propiedades del cuerpo del pincho
     */
    private BodyDef propiedadesCuerpo;
    /**
     * Body del pincho
     */
    private Body cuerpo;//el cuerpo
    /**
     * Propiedades Fisicas del cuerpo
     */
    private FixtureDef propiedadesFisicaCuerpo;

    /**
     * Fixture del pincho
     */
    private Fixture fixture;

    /**
     * Constructor del pincho
     * @param m mundo del pincho
     * @param x pos x del pincho
     * @param y pos y del pincho
     */
    public Pincho(World m, float x, float y){
        this.mundo=m;
        sprite=new Sprite(new Texture("texturas/spike2.png"));
        //  float anchuraSprite=5f;
        // float alturaSprite=5f;
        sprite.setBounds(x,y,1,2);
        this.setBounds(x,y,1,2);
        this.propiedadesCuerpo=new BodyDef();//Establecemos las propiedades del cuero;
        propiedadesCuerpo.type=BodyDef.BodyType.StaticBody;
        propiedadesCuerpo.position.set(sprite.getX(),sprite.getY());
        cuerpo=mundo.createBody(propiedadesCuerpo);
        propiedadesFisicaCuerpo=new FixtureDef();
        propiedadesFisicaCuerpo.shape=new PolygonShape();
        ((PolygonShape)propiedadesFisicaCuerpo.shape).setAsBox(sprite.getWidth()/2,sprite.getHeight()/2);//establecemos el box2d
        propiedadesFisicaCuerpo.density=1f;
     fixture=   cuerpo.createFixture(propiedadesFisicaCuerpo);
     fixture.setUserData("pincho");

        sprite.setOrigin(this.sprite.getWidth()/2,this.sprite.getHeight()/2);


    }

    /**
     * Funcion para dibujar el sprite del pincho
     * @param batch del pincho
     * @param parentAlpha transparencia del pincho
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(cuerpo.getPosition().x-sprite.getWidth()/2,cuerpo.getPosition().y-sprite.getHeight()/2);
        sprite.setRotation(cuerpo.getAngle());
        this.setPosition(sprite.getX(),sprite.getY());
        sprite.draw(batch);
    }

    /**
     * Getter del cuerpo
     * @return  cuerpo del pincho
     */
    public Body getCuerpo() {
        return cuerpo;
    }

    /**
     * Getter del sprite
     * @return el sprite del pincho
     */
    public Sprite getSprite() {
        return sprite;
    }





}
