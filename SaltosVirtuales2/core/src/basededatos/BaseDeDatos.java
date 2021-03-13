package basededatos;

/**
 * Interfaz que uso para realizar la base de datos
 * @author nicoc
 */
public interface BaseDeDatos{
    /**
     * Funcion para guardar la puntuacion en la bbdd
     * @param puntuacion
     */
    public void GuardarPuntuacion (int puntuacion);

    /**
     * Funcion para cargar la puntuacion de la bbdd
     * @return la puntuacion en int
     */
    public int  cargarPuntuacion ();

    /**
     * Funcion para guardar las muertes
     * @param muertes entero donde pasamos la muerte
     */
    public void guardarMuertes(int muertes);

    /**
     * Fucnion para cargar las muertes de la bbdd
     * @return las muertes que hemos tenido
     */
    public int cargarMuertes();

}
