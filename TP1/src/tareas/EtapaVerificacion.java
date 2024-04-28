package tareas;

import java.util.Random;

public class EtapaVerificacion {
  private Registros registros;
  private static final int DURACION_ITERACION = 15;

  public EtapaVerificacion(Registros registros) {
    this.registros = registros;
  }

  private class ThreadVerif implements Runnable {
    public void run() {
      Random random = new Random();
      while (true) {
        if (registros.getConfirmadas_size() == 0) {
          // Wait for a random amount of time before trying again
          try {
            Thread.sleep(random.nextInt(200, 400)); // 2-4 sg
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          // Exit the loop if there are no more confirmed reservations
          if (registros.getConfirmadas_size() == 0) {
            System.out.println("--------- Thread VERIFICACION: finished ---------");
            System.out.flush();
            break;
          }
        }

        Asiento randomAsiento = registros.get_reserva(2);

        // Synchronize on the randomAsiento to avoid conflicts with other threads
        synchronized (randomAsiento) {
          if (randomAsiento.getChecked() == true) {
            // Perform the verification
            if (registros.eliminar_reserva(2, randomAsiento)) {
              registros.registrar_reserva(3, randomAsiento);
            }
          }
        }
        try {
          Thread.sleep(DURACION_ITERACION);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void ejecutarEtapa() {
    Thread t1 = new Thread(new ThreadVerif());
    Thread t2 = new Thread(new ThreadVerif());
    t1.start();
    t2.start();
  }

}