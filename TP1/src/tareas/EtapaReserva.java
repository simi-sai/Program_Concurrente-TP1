package tareas;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class EtapaReserva {
  private Registros registros;
  private static final int DURACION_ITERACION = 300; // 500 milliseconds (for now)
  private AtomicInteger ASIENTOS_DISPONIBLES = new AtomicInteger(0);

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
      while (ASIENTOS_DISPONIBLES.get() < 186) {
        int randomRow;
        int randomColumn;
        Asiento randomAsiento;
        do {
          // Reserve the random seat
          randomRow = random.nextInt(31);
          randomColumn = random.nextInt(6);
          randomAsiento = registros.getAsiento(randomRow, randomColumn);
        } while (randomAsiento.getEstadoReserva() != 0);
        synchronized (randomAsiento) {
          randomAsiento.reservar();
          registros.registrar_reserva(0, randomAsiento);
          ASIENTOS_DISPONIBLES.incrementAndGet();
        }
        // Sleep for a while
        try {
          Thread.sleep(DURACION_ITERACION); // The thread will sleep before attempting the next reservation
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        // if (ASIENTOS_DISPONIBLES.get() == 186) {
        // break;
        // }
      }
      System.out.println("------------------ All seats are reserved. Exiting. -----------------");
      System.out.flush();
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
