package tareas;

import java.util.Random;

public class EtapaReserva {
  private Registros registros;
  private static final int DURACION_ITERACION = 100; // 100 milliseconds (for now)

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
        if (allSeatsReserved()) {
          System.out.println("------------------ All seats are reserved. Exiting. -----------------");
          System.out.flush();
          break;
        }
        // Generate random row and column within the array bounds
        int randomRow = random.nextInt(31);
        int randomColumn = random.nextInt(6);
        // Get the random Asiento object
        Asiento randomAsiento = registros.getAsiento(randomRow, randomColumn);
        synchronized (randomAsiento) {
          // Check if the seat is free (estado == 0)
          if (randomAsiento.getEstadoReserva() == 0) {
            // Reserve the seat
            randomAsiento.reservar();
            registros.registrar_reserva(0, randomAsiento);
          }
        }
        try {
          Thread.sleep(DURACION_ITERACION); // The thread will sleep before attempting the next reservation
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

    private boolean allSeatsReserved() {
      for (int i = 0; i < 31; i++) {
        for (int j = 0; j < 6; j++) {
          if ((registros.getMatriz())[i][j].getEstadoReserva() == 0) {
            return false; // At least one seat is not reserved
          }
        }
      }
      return true; // All seats are reserved
    }
  }

  public void ejecutarEtapa() {
    // Initialize all seats
    Thread thread1 = new Thread(new ThreadReserva());
    Thread thread2 = new Thread(new ThreadReserva());
    Thread thread3 = new Thread(new ThreadReserva());
    thread1.start();
    thread2.start();
    thread3.start();
  }
}
