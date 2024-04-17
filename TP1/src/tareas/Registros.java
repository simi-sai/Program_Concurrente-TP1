package tareas;

import java.util.ArrayList;
import java.util.Random;

public class Registros {
  // Atributos
  private ArrayList<Asiento> reservas_pendientes;
  private ArrayList<Asiento> reservas_confirmadas;
  private ArrayList<Asiento> reservas_canceladas;
  private ArrayList<Asiento> reservas_verificadas;
  private Asiento[][] matriz_asientos;
  private final Object lockPendientes = new Object();
  private final Object lockCanceladas = new Object();
  private final Object lockConfirmadas = new Object();
  private final Object lockVerificadas = new Object();

  // Constructor
  public Registros() {
    reservas_pendientes = new ArrayList<Asiento>();
    reservas_confirmadas = new ArrayList<Asiento>();
    reservas_canceladas = new ArrayList<Asiento>();
    reservas_verificadas = new ArrayList<Asiento>();
    matriz_asientos = new Asiento[31][6]; // Initialize the array
    for (int i = 0; i < 31; i++) {
      for (int j = 0; j < 6; j++) {
        matriz_asientos[i][j] = new Asiento(i, j);
      }
    }
  }

  /**
   * A method to get a reservation based on the type provided.
   *
   * The synchronized block in Java ensures that only one thread can
   * execute the synchronized code block at a time.
   * 
   * @param tipo Type of reservation to get
   * @return The reservation seat retrieved
   */
  public Asiento get_reserva(int tipo) {
    Random random = new Random();
    int reservaIndex;
    Asiento asiento = null;
    switch (tipo) {

      case 0:
        /*
         * lockPendientes is used as the monitor object for synchronization.
         * This means
         * that only one thread at a time can enter the synchronized block that
         * is
         * synchronized on lockPendientes.
         */
        synchronized (lockPendientes) {
          if (!reservas_pendientes.isEmpty()) {
            reservaIndex = random.nextInt(reservas_pendientes.size());
            asiento = reservas_pendientes.get(reservaIndex);
          }
        }
        break;
      case 1:
        synchronized (lockCanceladas) {
          if (!reservas_canceladas.isEmpty()) {
            reservaIndex = random.nextInt(reservas_canceladas.size());
            asiento = reservas_canceladas.get(reservaIndex);
          }
        }
        break;
      case 2:
        synchronized (lockConfirmadas) {
          if (!reservas_confirmadas.isEmpty()) {
            reservaIndex = random.nextInt(reservas_confirmadas.size());
            asiento = reservas_confirmadas.get(reservaIndex);
          }
        }
        break;
      case 3:
        synchronized (lockVerificadas) {
          if (!reservas_verificadas.isEmpty()) {
            reservaIndex = random.nextInt(reservas_verificadas.size());
            asiento = reservas_verificadas.get(reservaIndex);
          }
        }
        break;
    }
    return asiento;
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
        synchronized (lockPendientes) {
          reservas_pendientes.add(asiento);
          System.out.printf("Reserva pendiente id: [%d:%d]\n", asiento.getColumna(), asiento.getFila());
        }
        break;
      case 1:
        synchronized (lockCanceladas) {
          reservas_canceladas.add(asiento);
          System.out.printf("Reserva cancelada id: [%d:%d]\n", asiento.getColumna(), asiento.getFila());
        }
        break;
      case 2:
        synchronized (lockConfirmadas) {
          reservas_confirmadas.add(asiento);
          System.out.printf("Reserva confirmada id: [%d:%d]\n", asiento.getColumna(), asiento.getFila());
        }
        break;
      case 3:
        synchronized (lockVerificadas) {
          reservas_verificadas.add(asiento);
          System.out.printf("Reserva verificada id: [%d:%d]\n", asiento.getColumna(), asiento.getFila());
        }
        break;
    }
  }

  public void eliminar_reserva(int tipo, Asiento asiento) {
    switch (tipo) {
      case 0:
        synchronized (lockPendientes) {
          reservas_pendientes.remove(asiento);
        }
        break;
      case 1:
        synchronized (lockCanceladas) {
          reservas_canceladas.remove(asiento);
        }
        break;
      case 2:
        synchronized (lockConfirmadas) {
          reservas_confirmadas.remove(asiento);
        }
        break;
      case 3:
        synchronized (lockVerificadas) {
          reservas_verificadas.remove(asiento);
        }
        break;
    }
  }

  public int getCanceladas_size() {
    synchronized (lockCanceladas) {
      return reservas_canceladas.size();
    }
  }

  public int getPendientes_size() {
    synchronized (lockPendientes) {
      return reservas_pendientes.size();
    }
  }

  public int getConfirmadas_size() {
    synchronized (lockConfirmadas) {
      return reservas_confirmadas.size();
    }
  }

  public int getVerificadas_size() {
    synchronized (lockVerificadas) {
      return reservas_verificadas.size();
    }
  }

  public Asiento[][] getMatriz() {
    return matriz_asientos;
  }
}
