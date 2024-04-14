package tareas;

import java.util.ArrayList;
import java.util.Random;

public class Registros {
  // Atributos
  private ArrayList<Asiento> reservas_pendientes;
  private ArrayList<Asiento> reservas_confirmadas;
  private ArrayList<Asiento> reservas_canceladas;
  private ArrayList<Asiento> reservas_verificadas;
  // private int estado_reserva;

  // Constructor
  public Registros() {
    reservas_pendientes = new ArrayList<Asiento>();
    reservas_confirmadas = new ArrayList<Asiento>();
    reservas_canceladas = new ArrayList<Asiento>();
    reservas_verificadas = new ArrayList<Asiento>();
  }

  /*
   * Tipos de reserva:
   * 0: reserva pendiente
   * 1: reserva cancelada
   * 2: reserva confirmada
   * 3: reserva verificada
   */
  public void registrar_reserva(int tipo, Asiento asiento) {
    switch (tipo) {
      case 0:
        reservas_pendientes.add(asiento);
        System.out.printf("Reserva pendiente id: [%d:%d]\n", asiento.getColumna(), asiento.getFila());
        break;
      case 1:
        reservas_canceladas.add(asiento);
        break;
      case 2:
        reservas_confirmadas.add(asiento);
        break;
      case 3:
        reservas_verificadas.add(asiento);
        break;
    }
  }

  // This method returns a random reservation, stil not finished
  public Asiento get_reserva(int tipo) {
    Random random = new Random();
    int reserva;
    Asiento asiento = null;
    switch (tipo) {
      case 0:
        reserva = random.nextInt(reservas_pendientes.size());
        asiento = reservas_pendientes.get(reserva);
        break;
      case 1:
        reserva = random.nextInt(reservas_canceladas.size());
        asiento = reservas_canceladas.get(reserva);
        break;
      case 2:
        reserva = random.nextInt(reservas_confirmadas.size());
        asiento = reservas_confirmadas.get(reserva);
        break;
      case 3:
        reserva = random.nextInt(reservas_verificadas.size());
        asiento = reservas_verificadas.get(reserva);
        break;
    }
    return asiento;
  }
}
