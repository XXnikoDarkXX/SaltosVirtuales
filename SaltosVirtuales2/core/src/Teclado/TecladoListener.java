package Teclado;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;

import java.util.ArrayList;

import Otros.Constantes;
import actores.ActorJugador;
import actores.Movimiento;
import actores.Objeto;

/**
 * Clase Teclado donde escuchara las tecla y el raton
 */
public class TecladoListener implements InputProcessor {

    /**
     * Jugador
     */
    private ActorJugador jugador;

    /**
     * Constructor del teclado
     * @param jugador le pasamos el jugador que estamos usando
     */
    public TecladoListener(ActorJugador jugador) {

        this.jugador = jugador;


    }

    /**
     * Funcion para al pulsar una tecla haga una accion el jugador
     * @param keycode tecla pulsada
     * @return una accion
     */
    @Override
    public boolean keyDown(int keycode) {

        Gdx.app.log("Tecla pulsada", "" + keycode);
        switch (keycode) {
            case Input.Keys.A:
                if (this.jugador.isEstaEnElSuelo() == true) {
                    jugador.iniciarMovimiento(Movimiento.SALTO);
                }
                break;

            case Input.Keys.F:
                jugador.setVivo(false);
                break;


            case Input.Keys.D:

                if (jugador.isPararTiempo()){


              jugador.iniciarMovimiento(Movimiento.PARAR);
            jugador.getCuerpo().setType(BodyDef.BodyType.StaticBody);
                }
                break;

        }
        return false;

    }

    /**
     * Funcion para cuando dejamos soltar la tecla
     * @param keycode tecla que dejamos de pulasr
     * @return false
     */
    @Override
    public boolean keyUp(int keycode) {

        switch (keycode) {
            case Input.Keys.A:
                jugador.finalizarMovimiento(Movimiento.SALTO);
                break;

            case Input.Keys.D:
                jugador.finalizarMovimiento(Movimiento.PARAR);
                jugador.getCuerpo().setType(BodyDef.BodyType.DynamicBody);

                jugador.setPararTiempo(false);
                break;



        }


        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Funcion para controlar el click o el touch del juego
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (screenY > Gdx.graphics.getHeight() / 3) {
            if (this.jugador.isEstaEnElSuelo() == true) {
                jugador.iniciarMovimiento(Movimiento.SALTO);
            }
        }

        return false;
    }

    /**
     * Funcion para cuando dejamos de pulsar el click
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (screenY > Gdx.graphics.getHeight() / 3) {
            jugador.finalizarMovimiento(Movimiento.SALTO);

        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }





}
