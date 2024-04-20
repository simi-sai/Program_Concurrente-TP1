import tareas.*;

public class Main {
  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    Registros registros = new Registros();
    EtapaReserva etapa1 = new EtapaReserva(registros);
    EtapaPago etapa2 = new EtapaPago(registros);
    EtapaValidacion etapa3 = new EtapaValidacion(registros);
    EtapaVerificacion etapa4 = new EtapaVerificacion(registros);
    Estadistica stat = new Estadistica(registros);
    Thread statThread = new Thread(stat);
    etapa1.ejecutarEtapa();
    etapa2.ejecutarEtapa();
    etapa3.ejecutarEtapa();
    etapa4.ejecutarEtapa();
    statThread.start();
    try {
      statThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    long endTime = System.currentTimeMillis();
    long elapsedTime = endTime - startTime;
    System.out.println("Tiempo de ejecución: " + (elapsedTime / 1000) + "s");
    System.out.flush();
  }
}
