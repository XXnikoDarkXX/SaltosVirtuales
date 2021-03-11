package BaseDatosEscritorio;

import basededatos.BaseDeDatos;

public class BaseDatosEscritorio implements BaseDeDatos {
    private int puntuacion;
    private int muertes;

    public BaseDatosEscritorio(){
        puntuacion=0;
        muertes=0;
    }

    @Override
    public void GuardarPuntuacion(int puntuacion) {
        this.puntuacion=puntuacion;
    }

    @Override
    public int cargarPuntuacion() {
        return puntuacion;
    }

    @Override
    public void guardarMuertes(int muertes) {

        this.muertes=muertes;

    }

    @Override
    public int cargarMuertes() {
        return this.muertes;
    }
}
