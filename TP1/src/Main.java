import java.util.Arrays;
import Tareas.Reserva;
import Tareas.Validacion;
import Tareas.Pagos;
import Tareas.Cancelacion;

public class Main {
  public static void main(String[] args) {
    // 186 pasajeros = 31 filas y 6 columnas
    Integer[][] Asientos = new Integer[31][6];

    Reserva r = new Reserva();
    Validacion v = new Validacion();
    Pagos p = new Pagos();
    Cancelacion c = new Cancelacion();
  }
}
