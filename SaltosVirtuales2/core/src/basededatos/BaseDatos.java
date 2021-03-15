package basededatos;

public abstract class BaseDatos {

    private int puntuacion;
    private int muertes;

public BaseDatos(){
    puntuacion=0;
    muertes=0;
}

public void aumentarPuntuacion(){
    puntuacion++;
}

public void aumentarMuertes(){
    muertes++;
}


    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getMuertes() {
        return muertes;
    }

    public void setMuertes(int muertes) {
        this.muertes = muertes;
    }

    public abstract void cargarPartida();

public abstract  void persistirPartida();
public abstract void ganarPartida();





}
