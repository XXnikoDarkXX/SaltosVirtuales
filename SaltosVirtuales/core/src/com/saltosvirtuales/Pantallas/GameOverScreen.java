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

public class GameOverScreen extends BaseScreen {


    private Stage stage;


    private Skin skin;


    private Image gameover;


    private TextButton jugar, menu;

    public GameOverScreen(final Principal game) {
        super(game);

        stage = new Stage(new FitViewport(640, 360));


        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));


        jugar = new TextButton("Volver a jugar", skin);
        menu = new TextButton("Menu", skin);


        gameover = new Image(game.getManager().get("texturas/gameover.png", Texture.class));


        jugar.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Here I go to the game screen again.
                game.setScreen(game.getGameScreen());
            }
        });

        menu.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // And here I go to the menu screen.
                game.setScreen(game.getMenuScreen());
            }
        });

        gameover.setPosition(320 - gameover.getWidth() / 2, 320 - gameover.getHeight()/2);
        jugar.setSize(200, 80);
        menu.setSize(200, 80);
        jugar.setPosition(Gdx.graphics.getWidth()/7, 50);
        menu.setPosition(Gdx.graphics.getWidth()/2, 50);


        stage.addActor(jugar);
        stage.addActor(gameover);
        stage.addActor(menu);
    }

    @Override
    public void show() {
        // Now this is important. If you want to be able to click the button, you have to make
        // the Input system handle input using this Stage. Stages are also InputProcessors. By
        // making the Stage the default input processor for this game, it is now possible to
        // click on buttons and even to type on input fields.
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        // When the screen is no more visible, you have to remember to unset the input processor.
        // Otherwise, input might act weird, because even if you aren't using this screen, you are
        // still using the stage for handling input.
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        // Dispose assets.
        skin.dispose();
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        // Just render things.
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}
