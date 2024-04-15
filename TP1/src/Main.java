
import tareas.EtapaReserva;
import tareas.Registros;
import tareas.EtapaPago;

public class Main {
  public static void main(String[] args) {
    Registros registros = new Registros();
    EtapaReserva etapa = new EtapaReserva(registros);
    EtapaPago etapa2 = new EtapaPago(registros);
    etapa.ejecutarEtapa();
    etapa2.ejecutarEtapa();
  }
}
