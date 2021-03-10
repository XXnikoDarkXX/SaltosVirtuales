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

public class TecladoListener implements InputProcessor {


    private ActorJugador jugador;


    public TecladoListener(ActorJugador jugador) {

        this.jugador = jugador;


    }

    @Override
    public boolean keyDown(int keycode) {

        Gdx.app.log("Tecla pulsada", "" + keycode);
        switch (keycode) {
            case Input.Keys.A:
                if (this.jugador.isEstaEnElSuelo() == true) {
                    jugador.iniciarMovimiento(Movimiento.SALTO);
                  /*  System.out.println("toco");

                    this.jugador.setMasSalto(true);

                    this.jugador.salto();*/
                }
                break;
            case Input.Keys.R:
                RotateByAction rotar = new RotateByAction();
                rotar.setAmount(-20);
                rotar.setDuration(0.3f);
                jugador.addAction(rotar);

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



        //public static final float PLAYER_SPEED=5f;


        }
        return false;

    }

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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (screenY > Gdx.graphics.getHeight() / 3) {
            if (this.jugador.isEstaEnElSuelo() == true) {
                jugador.iniciarMovimiento(Movimiento.SALTO);
            }
        }

        return false;
    }


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
