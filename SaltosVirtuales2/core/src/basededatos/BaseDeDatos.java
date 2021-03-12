package basededatos;

public interface BaseDeDatos{

    public void GuardarPuntuacion (int puntuacion);

    public int  cargarPuntuacion ();

    public void guardarMuertes(int muertes);
    public int cargarMuertes();

}
