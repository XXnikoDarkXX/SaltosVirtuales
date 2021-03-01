package actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Pincho extends Actor {

    protected Sprite sprite;//el sprite
    private World mundo; //el mundo
    private BodyDef propiedadesCuerpo;
    private Body cuerpo;//el cuerpo
    private FixtureDef propiedadesFisicaCuerpo;

    public Pincho(World m, float x, float y){
        this.mundo=m;

        sprite =new Sprite(new Texture("texturas/spike2.png"));
        sprite.setBounds(x,y,5,5);
        this.setBounds(x,y,5,5);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
       // sprite.setPosition(cuerpo.getPosition().x-sprite.getWidth()/2,cuerpo.getPosition().y-sprite.getHeight()/2);
       // sprite.setRotation(cuerpo.getAngle());
        sprite.setPosition(getX(),getY());
        this.setPosition(sprite.getX(),sprite.getY());
        sprite.draw(batch);
    }
}
