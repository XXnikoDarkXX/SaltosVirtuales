package Pantallas;



import com.badlogic.gdx.Screen;

import es.com.saltosvirtuales.Principal;

public abstract class BaseScreen implements Screen {
    protected Principal game;

    public BaseScreen(Principal game){
        this.game=game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    }

    @Override
    public void dispose() {

    }


}
