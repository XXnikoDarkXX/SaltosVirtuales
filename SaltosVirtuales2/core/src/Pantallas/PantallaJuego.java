package Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import Teclado.TecladoListener;
import actores.ActorJugador;
import actores.Objeto;
import actores.Pincho;
import basededatos.BaseDatos;

import es.com.saltosvirtuales.Principal;

/**
 * Clase donde tendremos el juego visualmente, tendremos camaras, el jugador, los pinchos, items escenarios ect
 * Desde aqui podremos interactuar con el mundo y dibujarlo todo
 * */
public class PantallaJuego extends BaseScreen {
    /**
     * Clase auxiliar para renderizar un mapa
     */
    private OrthogonalTiledMapRenderer renderer; //Clase auxiliar para renderizar un mapa.
    /**
     * Camara del mundo
     */
    private OrthographicCamera camera;

    /**
     * ArrayList de objetos
     */
    private ArrayList<Objeto> objetos;
    /**
     * Nos servirá para establecer que la pantalla se divide en tiles de 16 pixeles
     */
    public static final float unitScale = 1 / 16f; //Nos servirá para establecer que la pantalla se divide en tiles de 16 pixeles;

    /**
     * El batch
     */
    private SpriteBatch batch;

    /**
     * Los objetos que actuan como superficies en el juego
     */
    private ArrayList<Body> suelos;
    /**
     * ArrayList de caida
     */
    private ArrayList<Body> caida;
    /**
     * ArrayList para saber que hemos ganado
     */
    private ArrayList<Body> win;

    private BaseDatos bd;
//-------------------------------------------------
    /**
     * World del juego
     */
    private World world;
    /**
     * Jugador
     */
    private ActorJugador jugador;
    /**
     * Titulo que tiene el jugador
     */
    private String titulo;
    /**
     * Musica
     */
    private Music music;
    /**
     * ArrayList con todos los pinchos
     */
    private ArrayList<Pincho> pincho;
    /**
     * Teclado
     */
    private Teclado.TecladoListener teclin;
    /**
     * Clase auxiliar para saber colisiones de box2d
     */
    private Box2DDebugRenderer rend;
    //----------------------------------

    /**
     * El batchtexto para escribir texto en el juego
     */
    private SpriteBatch batchTexto;
    /**
     * El texto en pantalla fondo
     */
    private BitmapFont textoPantalla;
    /**
     * Base de datos
     */



    /**
     * Contructor de pantalla de jugo
     * @param game clase principal
     */
    public PantallaJuego(Principal game,BaseDatos bd) {
        super(game);
        this.bd=bd;



    }

    /**
     * Funcion donde mostramos y creamos los objetos, jugadores, pinchos ect
     */
    @Override
    public void show() {
        batch = new SpriteBatch();
        bd.cargarPartida();
        batchTexto = new SpriteBatch();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fuente/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.borderColor = new Color(0.1f, 0.1f, 0.1f, 1);
        parameter.borderWidth = 3f;
        parameter.incremental = true;
        textoPantalla = generator.generateFont(parameter);


        world = new World(new Vector2(0, -10), true);
        suelos = new ArrayList<Body>();
        caida = new ArrayList<Body>();
        win = new ArrayList<Body>();


        float w = Gdx.graphics.getWidth(); //Obtenemos la anchura de nuestra pantalla en pixels
        float h = Gdx.graphics.getHeight(); //Obtenemos la atura de nuestra pantalla en pixels
        ///-----------------------------------


        pincho = new ArrayList<Pincho>();

        pincho.add(new Pincho(world, 28.370468f, 4));
        pincho.add(new Pincho(world, 37, 4));
        pincho.add(new Pincho(world, 50, 4f));

        pincho.add(new Pincho(world, 52, 4));
        pincho.add(new Pincho(world, 67, 4.5f));

        pincho.add(new Pincho(world, 90, 4f));
        pincho.add(new Pincho(world, 309, 5.5f));
        pincho.add(new Pincho(world, 321, 3.2f));
        pincho.add(new Pincho(world, 346.40668f, 2.7637267f));
        pincho.add(new Pincho(world, 444f, 15));


        jugador = new ActorJugador(world, pincho, suelos, caida, win);


        //Creo la musica
        music = Gdx.audio.newMusic(Gdx.files.internal("audio/song.ogg"));



        //Creo los objetos que tendra el mapa
        objetos = new ArrayList<Objeto>();
        Texture texturaMoneda = new Texture("texturas/Coin1.png");
        Texture texturaCalavera = new Texture("texturas/calavera.png");
        Texture texturaReloj = new Texture("texturas/reloj.png");
        Texture texturaMusica = new Texture("texturas/music.png");
        Texture texturaParada=new Texture("texturas/parada.png");
        //moneda
        objetos.add(new Objeto(world, texturaMoneda, "moneda", 34, 5, 1, 2));
        objetos.add(new Objeto(world, texturaMoneda, "moneda", 40, 5, 1, 2));
        objetos.add(new Objeto(world, texturaMoneda, "moneda", 330, 5, 1, 2));
        objetos.add(new Objeto(world, texturaMoneda, "moneda", 60, 5, 1, 2));
        objetos.add(new Objeto(world, texturaMoneda, "moneda", 228, 3, 1, 2));
        objetos.add(new Objeto(world, texturaMoneda, "moneda", 470, 17, 1, 2));


        objetos.add(new Objeto(world, texturaCalavera, "calavera", 20, 5, 2, 2));
        //musica

        objetos.add(new Objeto(world, texturaMusica, "musica", "audio/music2.ogg", 227, 3, 2, 3));


        //relojes

        objetos.add(new Objeto(world, texturaReloj, "reloj", 328, 4, 0.7f, 0.8f, 3, 3));
        objetos.add(new Objeto(world,texturaReloj,"reloj",44,6,0.7f,0.8f,215,10));

        //tiempo
        objetos.add(new Objeto(world, texturaParada, "parada", 10, 5, 3, 2));



        teclin = new TecladoListener(jugador);
        Gdx.input.setInputProcessor(teclin);
        //stage.setKeyboardFocus(jugador);//Establecemos el foco del teclado en uno de los actoers o varios

        //----------------------------------
        TiledMap map = new TmxMapLoader().load("mapas/mapa1.tmx"); //Cargamos el tilemap desde assets
        renderer = new OrthogonalTiledMapRenderer(map, unitScale); //Establecemos el renderizado del mapa dividido en Tiles de 32 dp.
        //camera =new OrthographicCamera(10,10);
        //Establecemos el zoom de la cámara. 0.1 es más cercano que 1. Jugaremos con esto en clase

        camera = new OrthographicCamera(1, 1);//1 metro por  1 metro

         camera.setToOrtho(false, 12, 12);

        this.rend = new Box2DDebugRenderer();


        camera.position.x = 10;  //Establecemos la posición x de la cámara en función del número de tiles de la anchura. Jugaremos con esto en clase
        camera.position.y = 7; //Establecemos la posición x de la cámara en función del número de tiles de la anchura. Jugaremos con esto en clase

        //	camera.update(); //Colocamos la Cámara.


        //Para recoger el suelo
        for (MapObject objeto : map.getLayers().get("Suelo").getObjects()) {
            BodyDef propiedadesRectangulo = new BodyDef(); //Establecemos las propiedades del cuerpo
            propiedadesRectangulo.type = BodyDef.BodyType.StaticBody;
            Body rectanguloSuelo = world.createBody(propiedadesRectangulo);
            suelos.add(rectanguloSuelo);
            FixtureDef propiedadesFisicasRectangulo = new FixtureDef();
            Shape formaRectanguloSuelo =
                    getRectangle((RectangleMapObject) objeto);
            propiedadesFisicasRectangulo.shape = formaRectanguloSuelo;
            propiedadesFisicasRectangulo.density = 1f;
            rectanguloSuelo.createFixture(propiedadesFisicasRectangulo);


        }


        for (MapObject objeto : map.getLayers().get("caida").getObjects()) {
            BodyDef propiedadesRectangulo = new BodyDef(); //Establecemos las propiedades del cuerpo
            propiedadesRectangulo.type = BodyDef.BodyType.StaticBody;
            Body rectanguloSuelo = world.createBody(propiedadesRectangulo);
            caida.add(rectanguloSuelo);
            FixtureDef propiedadesFisicasRectangulo = new FixtureDef();
            Shape formaRectanguloSuelo =
                    getRectangle((RectangleMapObject) objeto);
            propiedadesFisicasRectangulo.shape = formaRectanguloSuelo;
            propiedadesFisicasRectangulo.density = 1f;
            rectanguloSuelo.createFixture(propiedadesFisicasRectangulo);


        }
        for (MapObject objeto : map.getLayers().get("Win").getObjects()) {
            BodyDef propiedadesRectangulo = new BodyDef(); //Establecemos las propiedades del cuerpo
            propiedadesRectangulo.type = BodyDef.BodyType.StaticBody;
            Body rectanguloSuelo = world.createBody(propiedadesRectangulo);
            win.add(rectanguloSuelo);
            FixtureDef propiedadesFisicasRectangulo = new FixtureDef();
            Shape formaRectanguloSuelo =
                    getRectangle((RectangleMapObject) objeto);
            propiedadesFisicasRectangulo.shape = formaRectanguloSuelo;
            propiedadesFisicasRectangulo.density = 1f;
            rectanguloSuelo.createFixture(propiedadesFisicasRectangulo);


        }


    }


    /**
     * Funcion pra renderizar el juego
     * @param delta frames
     */
    @Override
    public void render(float delta) {
        music.play();
        Gdx.gl.glClearColor(1, 1, 0, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //camera.update(); //Colocamos la Cámara.


        if (jugador.getPinchoDestruido()!=null) {
            world.destroyBody(jugador.getPinchoDestruido());
            jugador.setPinchoDestruido(null);
            jugador.setInmortalidad(false);
        }
        long momento=System.currentTimeMillis();
        jugador.seguir(camera,momento);
        renderer.setView(camera); //Establecemos la vista del mundo a través de la cámara.
        renderer.render(); //Renderizamos la vista
        batch.setProjectionMatrix(camera.combined);



        world.step(Gdx.graphics.getDeltaTime(), 6, 2);



        batch.begin();

        //hacemos las funciones del jugador
        jugador.act(Gdx.graphics.getDeltaTime());

        //En caso de que algun objeto colisione con el jugador
        for (int i = 0; i < objetos.size(); ++i) {
            objetos.get(i).draw(batch,0);
            checkCollision(jugador, objetos.get(i));
        }


        for (int i = 0; i < pincho.size(); ++i) {
            pincho.get(i).draw(batch,0);
        }

        jugador.draw(batch,0);



        batch.end();



        batchTexto.begin();

         switch (bd.getMuertes()){
           case 0:
               titulo="pro";
               break;
           case 1:
               titulo="noob";
               break;
           case 3:
               titulo="lammer";
               break;
           case 5:
               titulo="fracaso";
               break;
       }

        textoPantalla.draw(batchTexto,"Muertes: "+bd.getMuertes()+" Eres un "+titulo,150,-Gdx.graphics.getHeight()/30+Gdx.graphics.getHeight(),Gdx.graphics.getWidth()/30,1,false);
           textoPantalla.draw(batchTexto,"Puntuacion: "+bd.getPuntuacion(),70,Gdx.graphics.getHeight()/30,Gdx.graphics.getWidth()/30,1,false);

        if (jugador.isGanar()){
            textoPantalla.draw(batchTexto,"Ganasteeee!!",150,Gdx.graphics.getHeight()/2,Gdx.graphics.getWidth()/2,1,false);
            bd.ganarPartida();
            music.stop();

        }

        batchTexto.end();

        camera.update();
        if (jugador.isVivo()==false){
            bd.getMuertes();
            bd.aumentarMuertes();

            bd.persistirPartida();
            music.stop();
         //   bd.guardarMuertes(bd.cargarMuertes()+1);
            System.out.println("Posicion X: "+ jugador.getCuerpo().getPosition().x+" Posicion y "+jugador.getCuerpo().getPosition().y);
            restablecer();


        }
        rend.render(world, camera.combined);
    }
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

        music.stop();

       dispose();
    }

    /**
     * Funcion para eliminar espacio memoria
     */
    @Override
    public void dispose() {
        world.dispose();
        music.dispose();
        renderer.dispose(); //Destruimos el objeto que renderiza un mapa, para no tener filtraciones de memoria

        rend.dispose();
    }

    /**
     * Fucnion para sacar polygonShape para los suelos
     * @param rectangleObject un rectanble objeto que le pasamos
     * @return un PolygonShape
     */
    private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / 16f,
                (rectangle.y + rectangle.height * 0.5f) / 16f);
        polygon.setAsBox(rectangle.width * 0.5f / 16f,
                rectangle.height * 0.5f / 16f,
                size,
                0.0f);
        return polygon;
    }

    /**
     * Funcion para revisar las colisiones que hace el jugador con los objetos
     * @param jugador jugador
     * @param  objeto al cual colisiona
     */
    public void checkCollision(ActorJugador jugador, Objeto objeto) {
        if(Intersector.overlaps(jugador.getSprite().getBoundingRectangle(), objeto.getSprite().getBoundingRectangle())){
            if(objeto.getNombreObjeto().equalsIgnoreCase("moneda")){

                if (objeto.isMostrar()==false){
                    //System.out.println(jugador.getPuntuacion());
                }else {
                    jugador.setPuntuacion((byte) (jugador.getPuntuacion() + 1));
                    System.out.println("Recogistes una moneda");

                    objeto.setMostrar(false);
                    bd.getPuntuacion();
                    bd.aumentarPuntuacion();



                }

            }
            if (objeto.getNombreObjeto().equalsIgnoreCase("calavera")){
                if (!objeto.isMostrar()==false){
                    jugador.setInmortalidad(true);

                    objeto.setMostrar(false);

                }

            }

            if (objeto.getNombreObjeto().equalsIgnoreCase("parada")){
                if (!objeto.isMostrar()==false){
                    jugador.setPararTiempo(true);

                    objeto.setMostrar(false);

                }

            }





            if (objeto.getNombreObjeto().equalsIgnoreCase("reloj")){
                if (!objeto.isMostrar()==false){

                    System.out.println("reloj");
                    objeto.setMostrar(false);

                    jugador.getCuerpo().setTransform(objeto.getCambioX(),objeto.getCambioY(),jugador.getCuerpo().getAngle());



                }
            }

            if (objeto.getNombreObjeto().equalsIgnoreCase("musica")){


                if (!objeto.isMostrar()==false){
                    music.stop();

                    this.music=Gdx.audio.newMusic(Gdx.files.internal(objeto.getNombreMusica()));
                    objeto.setMostrar(false);




                }





            }


        }




    }

    public void checkCollision(ActorJugador jugador, ArrayList<Objeto>objetos) {
        for(Objeto spriteGroup : objetos) {
            checkCollision(jugador, spriteGroup);
        }
    }

    /**
     * Funcion para restablecer el juego
     */
    private void restablecer(){
        music = Gdx.audio.newMusic(Gdx.files.internal("audio/song.ogg"));
        jugador.setVivo(true);


        jugador.getCuerpo().setTransform(5,6.5f,jugador.getCuerpo().getAngle());
        for (int i=0;i<objetos.size();i++){

            if ((!objetos.get(i).getNombreObjeto().equalsIgnoreCase("calavera"))) {

                objetos.get(i).setMostrar(true);
            }
            if(objetos.get(i).getNombreObjeto().equalsIgnoreCase("moneda")){

                //objetos.get(i).setMostrar(false);

            }
        }



        for (int i=0;i<pincho.size();i++){
            if (pincho.get(i).getCuerpo()!=null){
                world.destroyBody(pincho.get(i).getCuerpo());
            }

            pincho.get(i).getSprite().getTexture().dispose();
        }
        pincho = new ArrayList<Pincho>();

        pincho.add(new Pincho(world, 28.370468f, 4));
        pincho.add(new Pincho(world, 37, 4));
        pincho.add(new Pincho(world, 50, 4f));

        pincho.add(new Pincho(world, 52, 4));
        pincho.add(new Pincho(world, 67, 4.5f));

        pincho.add(new Pincho(world, 90, 4f));
        pincho.add(new Pincho(world, 309, 5.5f));
        pincho.add(new Pincho(world, 321, 3.2f));
        pincho.add(new Pincho(world, 346.40668f, 2.7637267f));
        pincho.add(new Pincho(world, 444f, 15));

    }
}
