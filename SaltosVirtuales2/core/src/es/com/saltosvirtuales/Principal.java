package es.com.saltosvirtuales;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

import Otros.Constantes;
import Otros.Manager;
import Pantallas.Pantalla;
import Pantallas.PantallaJuego;
import Teclado.Teclado;
import actores.ActorJugador;
import actores.Jugador;
import actores.Objeto;
import actores.Pincho;

public class Principal extends Game {
	private OrthogonalTiledMapRenderer renderer; //Clase auxiliar para renderizar un mapa.
	private OrthographicCamera camera; //Cámara a través de la que veremos el mundo.
	private static int WIDTH; //Aquí almacenaremos la anchura en tiles
	private static int HEIGHT; //Aquí almacenaremos la altura en tiles


	//
		private ArrayList<Objeto>monedas;
	//

	public static final float unitScale = 1/16f; //Nos servirá para establecer que la pantalla se divide en tiles de 32 pixeles;
	private Stage stage;//lo usaremos para dibujar un actor
	/**
	 * El batch
	 */
	private SpriteBatch batch;

	/**
	 * Los objetos que actuan como superficies en el juego
	 */
	private ArrayList<Body> suelos;
//-------------------------------------------------

	private World world;
	private ActorJugador jugador;
	private Manager manager;
	private Sound jumpSound, dieSound;
	private Music music;

	private Pincho pincho;
	private TecladoListener teclin;

	private Box2DDebugRenderer rend;
	//----------------------------------
	@Override
	public void create() {
	batch=new SpriteBatch();

		world= new World(new Vector2(0, -10), true);
		suelos=new ArrayList<Body>();
		stage=new Stage(new FillViewport(100,30));
		//stage=new Stage((new ScreenViewport()));//Inicializmos el Stage
		float w = Gdx.graphics.getWidth(); //Obtenemos la anchura de nuestra pantalla en pixels
		float h = Gdx.graphics.getHeight(); //Obtenemos la atura de nuestra pantalla en pixels
		///-----------------------------------
		manager=new Manager();
	//	Gdx.input.setInputProcessor(stage);//añadimos a nuestro Stagee el metodo de entrada

		pincho=new Pincho(world,50,4.3f);
		jugador=new ActorJugador(world,pincho,suelos);
		stage.addActor(jugador);
		stage.addActor(pincho);


		//Creo los objetos que tendra el mapa
		monedas=new ArrayList<Objeto>();
		Texture texturaMoneda=new Texture("texturas/Coin1.png");
		monedas.add(new Objeto(world,texturaMoneda,"moneda",40,5,4,4));



		teclin=new TecladoListener(jugador,monedas);
		Gdx.input.setInputProcessor(teclin);
		stage.setKeyboardFocus(jugador);//Establecemos el foco del teclado en uno de los actoers o varios

		//----------------------------------
		TiledMap map = new TmxMapLoader().load("mapas/mapa1.tmx"); //Cargamos el tilemap desde assets
		renderer = new OrthogonalTiledMapRenderer(map, unitScale); //Establecemos el renderizado del mapa dividido en Tiles de 32 dp.
		camera =new OrthographicCamera(10,10);
		//Establecemos el zoom de la cámara. 0.1 es más cercano que 1. Jugaremos con esto en clase





		WIDTH = ((TiledMapTileLayer) map.getLayers().get(0)).getWidth(); //Obtenemos desde el mapa el número de tiles de ancho de la 1º Capa
		HEIGHT = ((TiledMapTileLayer) map.getLayers().get(0)).getHeight(); //Obtenemos desde el mapa el número de tiles de alto de la 1º Capa
		camera.setToOrtho(false, WIDTH,HEIGHT);
		System.out.println(WIDTH+""+HEIGHT);
		this.rend=new Box2DDebugRenderer();


		camera.position.x = WIDTH/2;  //Establecemos la posición x de la cámara en función del número de tiles de la anchura. Jugaremos con esto en clase
		camera.position.y = HEIGHT/2; //Establecemos la posición x de la cámara en función del número de tiles de la anchura. Jugaremos con esto en clase

		camera.update(); //Colocamos la Cámara.



		for (MapObject objeto:map.getLayers().get("Suelo").getObjects()){
			BodyDef propiedadesRectangulo= new BodyDef(); //Establecemos las propiedades del cuerpo
			propiedadesRectangulo.type = BodyDef.BodyType.StaticBody;
			Body rectanguloSuelo = world.createBody(propiedadesRectangulo);
			suelos.add(rectanguloSuelo);
			FixtureDef propiedadesFisicasRectangulo=new FixtureDef();
			Shape formaRectanguloSuelo=
					getRectangle((RectangleMapObject)objeto);
			propiedadesFisicasRectangulo.shape = formaRectanguloSuelo;
			propiedadesFisicasRectangulo.density = 1f;
			rectanguloSuelo.createFixture(propiedadesFisicasRectangulo);

		}





	}


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

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 0, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//camera.update(); //Colocamos la Cámara.


		renderer.setView(camera); //Establecemos la vista del mundo a través de la cámara.
		batch.setProjectionMatrix(camera.combined);

		stage.act(Gdx.graphics.getDeltaTime());
		renderer.render(); //Renderizamos la vista
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		batch.begin();
		stage.draw();//dibujamos


		for (int i = 0; i < monedas.size(); ++i) {
			monedas.get(i).draw(batch,0);
		}


		batch.end();

		camera.update();

		rend.render(world,camera.combined);
	}


	@Override
	public void dispose() {
		world.dispose();

		renderer.dispose(); //Destruimos el objeto que renderiza un mapa, para no tener filtraciones de memoria
		stage.dispose();
		rend.dispose();

	}


}




