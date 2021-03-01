package es.com.saltosvirtuales;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;

import java.lang.reflect.Array;
import java.util.ArrayList;

import actores.ActorJugador;
import actores.Movimiento;
import actores.Objeto;

public class TecladoListener implements InputProcessor {


    private ActorJugador jugador;

    private ArrayList<Objeto>monedas;

    public TecladoListener(ActorJugador jugador,  ArrayList<Objeto> monedas){

        this.jugador=jugador;
        this.monedas=monedas;

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

        }
        return false;

    }

    @Override
    public boolean keyUp(int keycode) {

        switch (keycode) {
            case Input.Keys.A:
                jugador.finalizarMovimiento(Movimiento.SALTO);
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
        if(screenY>Gdx.graphics.getHeight()/3) {
            if (this.jugador.isEstaEnElSuelo() == true) {
                jugador.iniciarMovimiento(Movimiento.SALTO);
            }
        }

        checkCollision(jugador, monedas);

        return false;
        }





    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(screenY>Gdx.graphics.getHeight()/3) {
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



    /**
     * Funcion para saber recoger objetos
     * @param
     * @param objeto el objeto
     */
    public void checkCollision(ActorJugador jugador, Objeto objeto) {
        if(Intersector.overlaps(jugador.getSprite().getBoundingRectangle(), objeto.getSprite().getBoundingRectangle())){
            if(objeto.getNombreObjeto().equalsIgnoreCase("moneda")){
               jugador.setPuntuacion((byte) (jugador.getPuntuacion()+1));
            }
        }
        objeto.setMostrar(false);
    }


    public void checkCollision(ActorJugador jugador, ArrayList<Objeto>objetos) {
        for(Objeto spriteGroup : objetos) {
            checkCollision(jugador, spriteGroup);
        }
    }

}
