package tareas;

import java.util.ArrayList;
import java.util.Random;

public class Registros {
  private static ArrayList<Asiento> reservas_pendientes = new ArrayList<>();
  private static ArrayList<Asiento> reservas_confirmadas = new ArrayList<>();
  private static ArrayList<Asiento> reservas_canceladas = new ArrayList<>();
  private static ArrayList<Asiento> reservas_verificadas = new ArrayList<>();
  private static Asiento[][] matriz_asientos = new Asiento[31][6];
  private static Random random = new Random();
  private static Random random_2 = new Random();

  public Registros() {
    for (int i = 0; i < 31; i++) {
      for (int j = 0; j < 6; j++) {
        matriz_asientos[i][j] = new Asiento(i, j);
      }
    }
  }

  public Asiento get_reserva(int tipo) {
    int reservaIndex;
    Asiento asiento = null;

    switch (tipo) {
      case 0:
        synchronized (reservas_pendientes) {
          while (reservas_pendientes.isEmpty()) {
            try {
              reservas_pendientes.wait(); // Wait until there is a reservation added
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          reservaIndex = random.nextInt(reservas_pendientes.size());
          asiento = reservas_pendientes.get(reservaIndex);
          return asiento;
        }
      case 2:
        synchronized (reservas_confirmadas) {
          while (reservas_confirmadas.isEmpty()) {
            try {
              reservas_confirmadas.wait(); // Wait until there is a reservation added
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          reservaIndex = random_2.nextInt(reservas_confirmadas.size());
          asiento = reservas_confirmadas.get(reservaIndex);
          return asiento;
        }
      default:
        // Handle unexpected tipo values
        System.err.println("Invalid tipo value: " + tipo);
    }
    return null;
  }

  // Tipos de Reserva: 0 - Pendiente, 1 - Cancelada, 2 - Confirmada, 3 - Verificada
  public void registrar_reserva(int tipo, Asiento asiento) {
    switch (tipo) {
      case 0:
        synchronized (reservas_pendientes) {
          asiento.setEstado(1);
          reservas_pendientes.add(asiento);
          reservas_pendientes.notifyAll(); // Notify all threads waiting on this object so they can try get the lock
          return;
        }
      case 1:
        synchronized (reservas_canceladas) {
          asiento.setEstado(-1);
          reservas_canceladas.add(asiento);
          reservas_canceladas.notifyAll();
          return;
        }
      case 2:
        synchronized (reservas_confirmadas) {
          reservas_confirmadas.add(asiento);
          reservas_confirmadas.notifyAll();
          return;
        }
      case 3:
        synchronized (reservas_verificadas) {
          reservas_verificadas.add(asiento);
          reservas_verificadas.notifyAll();
          return;
        }
    }
  }

  public boolean eliminar_reserva(int tipo, Asiento asiento) {
    switch (tipo) {
      case 0:
        synchronized (reservas_pendientes) {
          return reservas_pendientes.remove(asiento);
        }
      case 2:
        synchronized (reservas_confirmadas) {
          return reservas_confirmadas.remove(asiento);
        }
      default:
      System.err.println("Invalid tipo value: " + tipo);  
      return false;
    }
  }
  
  public void setChecked(Asiento asiento) {
    asiento.setChecked();
  }

  public boolean getChecked(Asiento asiento) {
    return asiento.getChecked();
  }

  public int getCanceladas_size() {
    synchronized (reservas_canceladas) {
      return reservas_canceladas.size();
    }
  }

  public int getPendientes_size() {
    synchronized (reservas_pendientes) {
      return reservas_pendientes.size();
    }
  }

  public int getConfirmadas_size() {
    synchronized (reservas_confirmadas) {
      return reservas_confirmadas.size();
    }
  }

  public int getVerificadas_size() {
    synchronized (reservas_verificadas) {
      return reservas_verificadas.size();
    }
  }

  public Asiento getAsiento(int f, int c) {
    synchronized (matriz_asientos) {
      return matriz_asientos[f][c];
    }
  }

  public void imprimir_reservas() {
    System.out.println("\nPendientes: " + getPendientes_size());
    System.out.println("Canceladas: " + getCanceladas_size());
    System.out.println("Confirmadas: " + getConfirmadas_size());
    System.out.println("Verificadas: " + getVerificadas_size());
    System.out.println("Total: " + (getCanceladas_size() + getVerificadas_size()));
  }
}
