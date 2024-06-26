package tareas;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class EtapaReserva {
  private Registros registros;
  private static final int DURACION_ITERACION = 550;
  private AtomicInteger ASIENTOS_LIBRES = new AtomicInteger(0);

  public EtapaReserva(Registros registros) {
    this.registros = registros;
  }

  private class ThreadReserva implements Runnable {
    Random random = new Random();
    
    public void run() {
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
          registros.registrar_reserva(0, randomAsiento);
          ASIENTOS_LIBRES.incrementAndGet();
        }
        
        try {
          Thread.sleep(DURACION_ITERACION);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      System.out.println("--------- Thread RESERVA: finished ---------");
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
