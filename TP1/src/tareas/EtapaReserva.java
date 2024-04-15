package tareas;

import java.util.Random;

public class EtapaReserva {
  private Asiento[][] asientos = new Asiento[31][6];
  private Registros registros;

  public EtapaReserva(Registros registros) {
    this.registros = registros;
  }

  private class ThreadReserva implements Runnable {
    /**
     * Implementation of the run method for the ThreadReserva class.
     * This method runs in a loop, attempting to reserve random seats until all
     * seats are reserved.
     */
    @Override
    public void run() {
      // Create a random object for generating random numbers
      Random random = new Random();
      // Loop indefinitely
      while (true) {
        // Generate random row and column within the array bounds
        int randomRow = random.nextInt(31);
        int randomColumn = random.nextInt(6);
        // Get the random Asiento object
        Asiento randomAsiento = asientos[randomRow][randomColumn];
        // Synchronize on the randomAsiento to avoid conflicts with other threads
        synchronized (randomAsiento) {
          // Check if the seat is free (estado == 0)
          if (randomAsiento.getEstadoReserva() == 0) {
            // Reserve the seat
            randomAsiento.reservar();
            // If registros is not null, register the reservation
            if (registros != null) {
              registros.registrar_reserva(0, randomAsiento);
            }
          }
        }
        // Check if all seats are reserved
        if (allSeatsReserved()) {
          // Exit the loop if all seats are reserved
          break;
        }
      }
    }

    private boolean allSeatsReserved() {
      for (int i = 0; i < 31; i++) {
        for (int j = 0; j < 6; j++) {
          if (asientos[i][j].getEstadoReserva() == 0) {
            return false; // At least one seat is not reserved
          }
        }
      }
      return true; // All seats are reserved
    }
  }

  public void ejecutarEtapa() {
    // Initialize all seats
    for (int i = 0; i < 31; i++) { // Loop through rows
      for (int j = 0; j < 6; j++) { // Loop through columns
        asientos[i][j] = new Asiento(i, j);
      }
    }
    Thread thread1 = new Thread(new ThreadReserva());
    Thread thread2 = new Thread(new ThreadReserva());
    Thread thread3 = new Thread(new ThreadReserva());
    thread1.start();
    thread2.start();
    thread3.start();
  }
}
