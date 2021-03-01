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
    private Fixture fixture;
    //

    public Objeto(World m,Texture texture,int positionX,int posicionY,int ancho,int alto){
        this.mundo=m;
        sprite=new Sprite((texture));
        //  float anchuraSprite=5f;
        // float alturaSprite=5f;
        sprite.setBounds(positionX,posicionY,ancho,alto);
        this.setBounds(positionX,posicionY,ancho,alto);
        this.propiedadesCuerpo=new BodyDef();//Establecemos las propiedades del cuero;
        propiedadesCuerpo.type=BodyDef.BodyType.StaticBody;
        propiedadesCuerpo.position.set(sprite.getX(),sprite.getY());
        cuerpo=mundo.createBody(propiedadesCuerpo);
        propiedadesFisicaCuerpo=new FixtureDef();
        propiedadesFisicaCuerpo.shape=new PolygonShape();
        ((PolygonShape)propiedadesFisicaCuerpo.shape).setAsBox(sprite.getWidth()/2,sprite.getHeight()/2);//establecemos el box2d
        propiedadesFisicaCuerpo.density=1f;


        sprite.setOrigin(this.sprite.getWidth()/2,this.sprite.getHeight()/2);
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(cuerpo.getPosition().x-sprite.getWidth()/2,cuerpo.getPosition().y-sprite.getHeight()/2);
        sprite.setRotation(cuerpo.getAngle());
        this.setPosition(sprite.getX(),sprite.getY());
        sprite.draw(batch);
    }

    public Body getCuerpo() {
        return cuerpo;
    }
}