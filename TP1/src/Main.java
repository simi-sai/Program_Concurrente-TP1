import tareas.EtapaReserva;
import tareas.Registros;

public class Main {
  public static void main(String[] args) {
    Registros registros = new Registros();
    EtapaReserva etapa = new EtapaReserva(registros);
    etapa.ejecutarEtapa();
  }
}
