import tareas.*;

public class Main {
  public static void main(String[] args) {
    Registros registros = new Registros();
    EtapaReserva etapa1 = new EtapaReserva(registros);
    EtapaPago etapa2 = new EtapaPago(registros);
    EtapaValidacion etapa3 = new EtapaValidacion(registros);
    EtapaVerificacion etapa4 = new EtapaVerificacion(registros);
    etapa1.ejecutarEtapa();
    etapa2.ejecutarEtapa();
    etapa3.ejecutarEtapa();
    etapa4.ejecutarEtapa();

    // Wait for all threads to finish and print the final state of the seats
    registros.printPendientes();
    registros.printConfirmadas();
    registros.printCanceladas();
    registros.printVerificadas();
  }
}
