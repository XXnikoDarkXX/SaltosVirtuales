package BaseDatosEscritorio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import basededatos.BaseDatos;

public class BaseDatosDesktop  extends BaseDatos {


    private static final String cadenaConexion = "jdbc:mysql://127.0.0.1:3306/geometry";
    private static final String usuario = "root";
    private static final String password = "admin";
    private static Connection conexion;


    private static Statement conectar() {
        try {
            conexion = DriverManager.getConnection(cadenaConexion, usuario, password);
            return conexion.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private void desconectar() {
        try {
            conexion.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public void cargarPartida() {
        Statement smt = null;
        try {
            smt = conectar();
            ResultSet resultados = smt.executeQuery("select * from partida");

            resultados.next();


            this.setMuertes(resultados.getInt("muertes"));
            this.setPuntuacion(resultados.getInt("puntucion"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                smt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        desconectar();
    }

    @Override
    public void persistirPartida() {
        Statement smt = null;
        try {
            smt = conectar();
            smt.executeUpdate("update partida set muertes=" + this.getMuertes());
            smt.executeUpdate("update partida set puntucion=" + this.getPuntuacion());

            smt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            try {
                smt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        desconectar();
    }

    @Override
    public void ganarPartida() {
        Statement smt = null;
        try {
            smt = conectar();
            smt.executeUpdate("update partida set muertes=" + 0);
            smt.executeUpdate("update partida set puntucion=" + 0);

            smt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            try {
                smt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        desconectar();
    }







}





