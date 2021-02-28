package Pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public  class Pantalla extends Game {
    private OrthogonalTiledMapRenderer renderer; //Clase auxiliar para renderizar un mapa.
    private OrthographicCamera camera; //Cámara a través de la que veremos el mundo.
    private static int WIDTH; //Aquí almacenaremos la anchura en tiles
    private static int HEIGHT; //Aquí almacenaremos la altura en tiles
    public static final float unitScale = 1/16f; //Nos servirá para establecer que la pantalla se divide en tiles de 32 pixeles;
    private Stage stage;//lo usaremos para dibujar un actor

    @Override
    public void create() {
        stage=new Stage((new ScreenViewport()));//Inicializmos el Stage
        float w = Gdx.graphics.getWidth(); //Obtenemos la anchura de nuestra pantalla en pixels
        float h = Gdx.graphics.getHeight(); //Obtenemos la atura de nuestra pantalla en pixels

        TiledMap map = new TmxMapLoader().load("mapas/mapa1.tmx"); //Cargamos el tilemap desde assets
        renderer = new OrthogonalTiledMapRenderer(map, unitScale); //Establecemos el renderizado del mapa dividido en Tiles de 32 dp.
        camera = new OrthographicCamera(); //Declaramos la cámara a través de la que veremos el mundo
        WIDTH = ((TiledMapTileLayer) map.getLayers().get(0)).getWidth(); //Obtenemos desde el mapa el número de tiles de ancho de la 1º Capa
        HEIGHT = ((TiledMapTileLayer) map.getLayers().get(0)).getHeight(); //Obtenemos desde el mapa el número de tiles de alto de la 1º Capa
        camera.setToOrtho(false, WIDTH,HEIGHT);

        camera.position.x =WIDTH/2;  //Establecemos la posición x de la cámara en función del número de tiles de la anchura. Jugaremos con esto en clase
        camera.position.y = HEIGHT/2; //Establecemos la posición x de la cámara en función del número de tiles de la anchura. Jugaremos con esto en clase
        camera.update(); //Colocamos la Cámara.

    }


    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 0, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update(); //Colocamos la Cámara.
        renderer.setView(camera); //Establecemos la vista del mundo a través de la cámara.
        stage.act(Gdx.graphics.getDeltaTime());
        renderer.render(); //Renderizamos la vista

        stage.draw();//dibujamos
    }


    @Override
    public void dispose() {
        renderer.dispose(); //Destruimos el objeto que renderiza un mapa, para no tener filtraciones de memoria
        stage.dispose();
    }
}
