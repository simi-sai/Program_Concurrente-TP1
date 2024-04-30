import tareas.*;

public class Main {
  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();

    Registros registros = new Registros();
    EtapaReserva reservas = new EtapaReserva(registros);
    EtapaPago pagos = new EtapaPago(registros);
    EtapaValidacion validaciones = new EtapaValidacion(registros);
    EtapaVerificacion verificaciones = new EtapaVerificacion(registros);
    Estadistica stat = new Estadistica(registros);
    Thread statThread = new Thread(stat);

    reservas.ejecutarEtapa();
    pagos.ejecutarEtapa();
    validaciones.ejecutarEtapa();
    verificaciones.ejecutarEtapa();
    statThread.start();

    try {
      statThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    long endTime = System.currentTimeMillis();
    long elapsedTime = endTime - startTime;
    
    registros.imprimir_reservas();
    System.out.println("Tiempo de ejecución: " + (elapsedTime / 1000) + "s");
    System.out.flush();
  }
}