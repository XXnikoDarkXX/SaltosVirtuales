package Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import es.com.saltosvirtuales.Principal;

/**
 * Clase Pantalla Menu
 */
public class PantallaMenu extends BaseScreen {
    /**
     * El escenario
     */
    private Stage stage;

    /**
     * El skin
     */
    private Skin skin;
    /**
     * Boton para play
     */
    private TextButton play;

    /**
     * Constructor de PantallaMenu
     * @param game el Principal
     */
    public PantallaMenu(final Principal game) {
        super(game);


        stage = new Stage(new FitViewport(640, 360));


        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        play = new TextButton("Play", skin);






        play.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                game.setScreen(game.getPantallaJuego());
            }
        });



        play.setSize(200, 80);

        play.setPosition(40, 140);



        stage.addActor(play);

    }


    /**
     * Mostramos el escenario
     */
    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {

        Gdx.input.setInputProcessor(null);
    }

    /**
     * Funcion para limpiar espacio en memoria
     */
    @Override
    public void dispose() {

        stage.dispose();
        skin.dispose();
    }

    /**
     * Funcion del renderizado
     * @param delta frames
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}
