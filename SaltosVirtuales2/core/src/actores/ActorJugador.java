package actores;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import Otros.Constantes;

public class ActorJugador extends Actor {

    protected Sprite sprite;//el sprite
    private World mundo; //el mundo
    private BodyDef propiedadesCuerpo;
    private Body cuerpo;//el cuerpo
    private FixtureDef propiedadesFisicaCuerpo;

    public  ActorJugador(World m){
        this.mundo=m;
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
        sprite.setOrigin(this.sprite.getWidth()/2,this.sprite.getHeight()/2);



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
        float rapidezY=this.cuerpo.getLinearVelocity().y;
       // cuerpo.setLinearVelocity(Constantes.PLAYER_SPEED,rapidezY);
    }
}
