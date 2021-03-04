package actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Jugador  extends Actor {
    private Sprite sprite; //Necesitamos una textura para dibujar el Sprite
    private World mundo; //El mundo
    Body cuerpo;
    SpriteBatch batchTexto; //Batch independiente del mundo, en el que se dibuja el texto de contador de muertes
    BitmapFont textoMuertes; //El texto que sale al ganar la partida
    int contadorMuertes; //Contador de las veces que ha muerto calipo


    public Jugador(World w){

        this.mundo=w;
        //Asignamos la textura
        sprite=new Sprite(new Texture(Gdx.files.internal("texturas/player.png")));
        //Esto es solo para retarlo en la x, porque la textura mira al otro lado.
        sprite.setFlip(true,false);
        //2x4 metros, dimensiones predefinidas. Tenemos un pollo hipervitaminado.
        sprite.setSize(2, 4);

        //Creando cuerpo
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 1;

        PolygonShape shape = new PolygonShape();

        //shape.setAsBox(camera.viewportWidth, 1);
        Vector2[] vertices=new Vector2[4];
        vertices[0]=new Vector2(0,0);
        vertices[1]=new Vector2(sprite.getWidth(),0);
        vertices[2]=new Vector2(sprite.getWidth(),sprite.getHeight());
        vertices[3]=new Vector2(0,sprite.getHeight());
        shape.set(vertices);

        fixtureDef.shape = shape;

        cuerpo=mundo.createBody(bodyDef);

        cuerpo.createFixture(fixtureDef);

        cuerpo.setTransform(0, 10, 0);

        shape.dispose();
        batchTexto=new SpriteBatch();


    }

    public Body getCuerpo(){
        return cuerpo;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //Si la posición es menor que el nivel del suelo, reseteo
        if(cuerpo.getPosition().y<0-sprite.getHeight()*3){
            //Reconstruimos el mundo con otro aleatorio distinto


            //Estas tres líneas anulan todas las fuerzas, y ponen al pollo en la posición predeterminada.
            cuerpo.setLinearVelocity(new Vector2(0,0));
            cuerpo.setAngularVelocity(0);
            cuerpo.setTransform(0,30,0);
            contadorMuertes++;

        }

        super.draw(batch, parentAlpha);
        this.setPosition(cuerpo.getPosition().x,cuerpo.getPosition().y);
        this.setRotation(cuerpo.getAngle());


        sprite.setScale(getScaleX(),getScaleY());
        sprite.setRotation(getRotation());
        sprite.setPosition(getX(),getY());
        sprite.setColor(getColor().r,getColor().g,getColor().b,getColor().a);
        sprite.setRotation(this.getRotation());
        sprite.draw(batch);


    }

    public void dibujarHUD(){
        batchTexto.begin();
        textoMuertes.draw(batchTexto, "Muertes: "+contadorMuertes,10, Gdx.graphics.getHeight()-10);
        textoMuertes.setColor(new Color(0.4f,0.4f,0,1));
        batchTexto.end();
    }

    public void resetearMuertes(){
        contadorMuertes=0;
    }


}
