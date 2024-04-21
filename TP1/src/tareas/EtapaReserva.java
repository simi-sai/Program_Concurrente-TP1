package tareas;

import java.util.Random;

public class EtapaReserva {
  private Registros registros;
  private static final int DURACION_ITERACION = 300; // 500 milliseconds (for now)

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
      int randomRow;
      int randomColumn;
      Asiento randomAsiento;
      // Loop indefinitely
      while (true) {
        if (allSeatsReserved()) {
          System.out.println("------------------ All seats are reserved. Exiting. -----------------");
          System.out.flush();
          break;
        }
        int n = 0;
        do {
          randomRow = random.nextInt(31);
          randomColumn = random.nextInt(6);
          randomAsiento = registros.getAsiento(randomRow, randomColumn);
          n++;
        } while (randomAsiento.getEstadoReserva() != 0 && n != 185);
        // Reserve the random seat
        synchronized (randomAsiento) {
          randomAsiento.reservar();
          registros.registrar_reserva(0, randomAsiento);
        }
        // Sleep for a while
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
          if ((registros.getAsiento(i, j).getEstado() == 0)) {
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
