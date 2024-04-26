package tareas;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class EtapaReserva {
  private Registros registros;
  private static final int DURACION_ITERACION = 40; // 500 milliseconds (for now)
  private AtomicInteger ASIENTOS_LIBRES = new AtomicInteger(0);

  public EtapaReserva(Registros registros) {
    this.registros = registros;
  }

  private class ThreadReserva implements Runnable {
    public void run() {
      // Create a random object for generating random numbers
      Random random = new Random();

      while (ASIENTOS_LIBRES.get() < 186) {
        int randomRow;
        int randomColumn;
        Asiento randomAsiento;

        do {
          randomRow = random.nextInt(31);
          randomColumn = random.nextInt(6);
          randomAsiento = registros.getAsiento(randomRow, randomColumn);
        } while (randomAsiento.getEstado() != 0);

        synchronized (randomAsiento) {
          randomAsiento.setEstado(1);
          registros.registrar_reserva(0, randomAsiento);
          ASIENTOS_LIBRES.incrementAndGet();
        }
        // Sleep for a while
        try {
          Thread.sleep(DURACION_ITERACION); // The thread will sleep before attempting the next reservation
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      System.out.println("------------------ All seats are reserved. Exiting. -----------------");
      System.out.flush();
    }
  }

  public void ejecutarEtapa() {
    Thread thread1 = new Thread(new ThreadReserva());
    Thread thread2 = new Thread(new ThreadReserva());
    Thread thread3 = new Thread(new ThreadReserva());
    thread1.start();
    thread2.start();
    thread3.start();
  }
}
