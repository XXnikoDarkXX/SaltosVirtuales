package es.com.saltosvirtuales;

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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FillViewport;

import java.util.ArrayList;

import javax.xml.soap.Text;

import Otros.Manager;
import actores.ActorJugador;
import actores.Objeto;
import actores.Pincho;

public class Principal extends Game {
	private OrthogonalTiledMapRenderer renderer; //Clase auxiliar para renderizar un mapa.
	private OrthographicCamera camera;

	private static int WIDTH; //Aquí almacenaremos la anchura en tiles
	private static int HEIGHT; //Aquí almacenaremos la altura en tiles


	//
		private ArrayList<Objeto> objetos;
		private ArrayList<String>RutasMusica;
	//

	public static final float unitScale = 1/16f; //Nos servirá para establecer que la pantalla se divide en tiles de 32 pixeles;
	//private Stage stage;//lo usaremos para dibujar un actor
	/**
	 * El batch
	 */
	private SpriteBatch batch;

	/**
	 * Los objetos que actuan como superficies en el juego
	 */
	private ArrayList<Body> suelos;
	private ArrayList<Body> caida;

//-------------------------------------------------

	private World world;
	private ActorJugador jugador;
	private Manager manager;
	private Sound jumpSound, dieSound;
	private Music music;

	private ArrayList<Pincho> pincho;
	private TecladoListener teclin;

	private Box2DDebugRenderer rend;
	//----------------------------------
	@Override
	public void create() {
	batch=new SpriteBatch();

		world= new World(new Vector2(0, -10), true);
		suelos=new ArrayList<Body>();
		caida=new ArrayList<Body>();

		//	stage=new Stage(new FillViewport(100,30));

		float w = Gdx.graphics.getWidth(); //Obtenemos la anchura de nuestra pantalla en pixels
		float h = Gdx.graphics.getHeight(); //Obtenemos la atura de nuestra pantalla en pixels
		///-----------------------------------
		manager=new Manager();
	//	Gdx.input.setInputProcessor(stage);//añadimos a nuestro Stagee el metodo de entrada

		pincho=new ArrayList<Pincho>();

		pincho.add(new Pincho(world,28.370468f, 4));
		pincho.add(new Pincho(world,37, 4));
		pincho.add(new Pincho(world,50,4f));

		pincho.add(new Pincho(world,52, 4));
		pincho.add(new Pincho(world,67,  4.5f));

		pincho.add(new Pincho(world,90,4f));
		pincho.add(new Pincho(world,309,5.5f));
		pincho.add(new Pincho(world,321,3.2f));
		pincho.add(new Pincho(world,346.40668f,   2.7637267f));
		pincho.add(new Pincho(world,444f,15));


		jugador=new ActorJugador(world,pincho,suelos,caida);



		//Creo la musica
		music=Gdx.audio.newMusic(Gdx.files.internal("audio/song.ogg"));



		//Creo los objetos que tendra el mapa
		objetos =new ArrayList<Objeto>();
		Texture texturaMoneda=new Texture("texturas/Coin1.png");
		Texture texturaCalavera=new Texture("texturas/calavera.png");
		Texture texturaReloj=new Texture("texturas/reloj.png");
		Texture texturaMusica=new Texture("texturas/music.png");
		//moneda
		objetos.add(new Objeto(world,texturaMoneda,"moneda",34, 5,1,2));
		objetos.add(new Objeto(world,texturaMoneda,"moneda",40,5,1,2));
		objetos.add(new Objeto(world,texturaMoneda,"moneda",330,5,1,2));
		objetos.add(new Objeto(world,texturaMoneda,"moneda",60,5,1,2));
		objetos.add(new Objeto(world,texturaMoneda,"moneda",228,3,1,2));
		objetos.add(new Objeto(world,texturaMoneda,"moneda",470,17,1,2));




		objetos.add(new Objeto(world,texturaCalavera,"calavera",20,5,2,2));
		//musica

		objetos.add(new Objeto(world,texturaMusica,"musica","audio/music2.ogg",227, 3,2,3));



		//relojes

		objetos.add(new Objeto(world,texturaReloj,"reloj",328,4,0.7f,0.8f,3,3));



		teclin=new TecladoListener(jugador);
		Gdx.input.setInputProcessor(teclin);
		//stage.setKeyboardFocus(jugador);//Establecemos el foco del teclado en uno de los actoers o varios

		//----------------------------------
		TiledMap map = new TmxMapLoader().load("mapas/mapa1.tmx"); //Cargamos el tilemap desde assets
		renderer = new OrthogonalTiledMapRenderer(map, unitScale); //Establecemos el renderizado del mapa dividido en Tiles de 32 dp.
		//camera =new OrthographicCamera(10,10);
		//Establecemos el zoom de la cámara. 0.1 es más cercano que 1. Jugaremos con esto en clase

		camera =new OrthographicCamera(1,1);//1 metro por  1 metro



		WIDTH = ((TiledMapTileLayer) map.getLayers().get(0)).getWidth(); //Obtenemos desde el mapa el número de tiles de ancho de la 1º Capa
		HEIGHT = ((TiledMapTileLayer) map.getLayers().get(0)).getHeight(); //Obtenemos desde el mapa el número de tiles de alto de la 1º Capa
		camera.setToOrtho(false, 12,12);
		System.out.println(WIDTH+""+HEIGHT);
		this.rend=new Box2DDebugRenderer();



		camera.position.x = 10;  //Establecemos la posición x de la cámara en función del número de tiles de la anchura. Jugaremos con esto en clase
		camera.position.y = 7; //Establecemos la posición x de la cámara en función del número de tiles de la anchura. Jugaremos con esto en clase

	//	camera.update(); //Colocamos la Cámara.


	//Para recoger el suelo
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


		for (MapObject objeto:map.getLayers().get("caida").getObjects()){
			BodyDef propiedadesRectangulo= new BodyDef(); //Establecemos las propiedades del cuerpo
			propiedadesRectangulo.type = BodyDef.BodyType.StaticBody;
			Body rectanguloSuelo = world.createBody(propiedadesRectangulo);
			caida.add(rectanguloSuelo);
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

		music.play();
		Gdx.gl.glClearColor(1, 1, 0, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//camera.update(); //Colocamos la Cámara.

		jugador.seguir(camera);
		renderer.setView(camera); //Establecemos la vista del mundo a través de la cámara.
		renderer.render(); //Renderizamos la vista
		batch.setProjectionMatrix(camera.combined);



		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		batch.begin();

		//hacemos las funciones del jugador
		jugador.act(Gdx.graphics.getDeltaTime());
		for (int i = 0; i < objetos.size(); ++i) {
			objetos.get(i).draw(batch,0);
			checkCollision(jugador, objetos.get(i));
		}


		for (int i = 0; i < pincho.size(); ++i) {
			pincho.get(i).draw(batch,0);
		}

	jugador.draw(batch,0);



		batch.end();

	//	System.out.println(jugador.getSprite().getX()+" y: "+jugador.getSprite().getY()+" cuerpo x: "+jugador.getCuerpo().getPosition().x+" y "+jugador.getCuerpo().getPosition().y);

		camera.update();
		if (jugador.isVivo()==false){
			music.stop();
		}
		rend.render(world, camera.combined);
	}


	@Override
	public void dispose() {
		world.dispose();
		music.dispose();
		renderer.dispose(); //Destruimos el objeto que renderiza un mapa, para no tener filtraciones de memoria
	//	stage.dispose();
		rend.dispose();

	}

	public void checkCollision(ActorJugador jugador, Objeto objeto) {
		if(Intersector.overlaps(jugador.getSprite().getBoundingRectangle(), objeto.getSprite().getBoundingRectangle())){
			if(objeto.getNombreObjeto().equalsIgnoreCase("moneda")){

				if (objeto.isMostrar()==false){
					//System.out.println(jugador.getPuntuacion());
				}else {
					jugador.setPuntuacion((byte) (jugador.getPuntuacion() + 1));
					System.out.println("Recogistes una moneda");
					objeto.setMostrar(false);
					//jugador.getPuntuacion();
				}

			}
			if (objeto.getNombreObjeto().equalsIgnoreCase("calavera")){
				if (!objeto.isMostrar()==false){
					jugador.setInmortalidad(true);
					System.out.println("eres inmortal ");
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






}




