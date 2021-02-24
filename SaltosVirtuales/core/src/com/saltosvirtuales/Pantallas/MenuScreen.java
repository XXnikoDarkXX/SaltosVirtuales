package com.saltosvirtuales.Pantallas;

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
import com.saltosvirtuales.Principal;

public class MenuScreen extends BaseScreen {

    /** The stage where all the buttons are added. */
    private Stage stage;

    /** The skin that we use to set the style of the buttons. */
    private Skin skin;

    /** The logo image you see on top of the screen. */
    private Image logo;

    /** The play button you use to jump to the game screen. */
    private TextButton play, credits;

    public MenuScreen(final Principal game) {
        super(game);


        stage = new Stage(new FitViewport(640, 360));


        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        play = new TextButton("Play", skin);
        credits = new TextButton("Credits", skin);


        logo = new Image(game.getManager().get("texturas/logo.png", Texture.class));


        play.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Take me to the game screen!
                game.setScreen(game.getGameScreen());
            }
        });

        credits.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        logo.setPosition(440 - logo.getWidth() / 2, 320 - logo.getHeight());
        play.setSize(200, 80);
        credits.setSize(200, 80);
        play.setPosition(40, 140);
        credits.setPosition(40, 40);


        stage.addActor(play);
        stage.addActor(logo);
        stage.addActor(credits);
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {

        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        // Dispose assets.
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}
