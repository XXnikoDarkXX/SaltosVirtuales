package Pantallas;



import com.badlogic.gdx.Screen;

import es.com.saltosvirtuales.Principal;

/**
 * Clase para implementar una pantalla por defecto
 */
public abstract class BaseScreen implements Screen {
    /**
     * Variable Principal que es donde ejecutamos la app
     */
    protected Principal game;

    /**
     * Constructor de BaseScreen
     * @param game Clase Principal
     */
    public BaseScreen(Principal game){
        this.game=game;
    }

    /**
     * Funcion para mostarr
     */
    @Override
    public void show() {

    }

    /**
     * Funcion de renderizado
     * @param delta frames
     */
    @Override
    public void render(float delta) {

    }


    @Override
    public void resize(int width, int height) {

    }

    /**
     * En caso de pausa
     */
    @Override
    public void pause() {

    }

    /**
     * Funcion en caso de resume
     */
    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /**
     * Funcion para liberar memoria
     */
    @Override
    public void dispose() {

    }


}
