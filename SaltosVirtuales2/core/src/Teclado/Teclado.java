package Teclado;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;

import actores.ActorJugador;
import actores.Jugador;
import actores.Movimiento;

public class Teclado extends InputListener {
    private ActorJugador jugador;


    public Teclado(ActorJugador jugador) {
        this.jugador = jugador;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
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

        return super.keyUp(event, keycode);
    }


    @Override
    public boolean keyUp(InputEvent event, int keycode) {

        switch (keycode) {
            case Input.Keys.A:
              jugador.finalizarMovimiento(Movimiento.SALTO);
                break;

        }

        return super.keyUp(event, keycode);
    }
}
