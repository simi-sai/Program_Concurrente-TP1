package tareas;

import java.util.Random;

public class EtapaValidacion {
  private Registros registros;
  private static final int DURACION_ITERACION = 20;

  public EtapaValidacion(Registros registros) {
    this.registros = registros;
  }

  // Define a thread that checks confirmed reservations, marking them as checked
  // or canceling them randomly
  private class ThreadValid implements Runnable {
    public void run() {
      // Create a random object for generating random numbers
      Random random = new Random();
      // Loop indefinitely
      while (true) {
        if (registros.getConfirmadas_size() == 0) {
          // Wait for a random amount of time before trying again
          try {
            Thread.sleep(random.nextInt(100, 300)); // 2-4 sg
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          // Exit the loop if there are no more confirmed reservations
          if (registros.getConfirmadas_size() == 0) {
            System.out.println("--------- Thread validacion: finished ---------");
            System.out.flush();
            break;
          }
        }
        // Takes a random reservation from the confirmed list
        Asiento randomAsiento = registros.get_reserva(2);
        int randomNumber = random.nextInt(100);
        // Synchronize on the randomAsiento to avoid conflicts with other threads
        synchronized (randomAsiento) {
          if (randomAsiento.getChecked() == false) {
            if (randomNumber < 90) {
              // Mark the reservation as checked
              randomAsiento.setChecked();
            } else {
              // Cancel the reservation: Set the seat as discarded, add to the canceled
              // reservations list, and remove from confirmed list
              randomAsiento.setEstado(-1);
              if (registros.eliminar_reserva(2, randomAsiento)) {
                registros.registrar_reserva(1, randomAsiento);
              }
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
    Thread t1 = new Thread(new ThreadValid());
    Thread t2 = new Thread(new ThreadValid());
    Thread t3 = new Thread(new ThreadValid());
    t1.start();
    t2.start();
    t3.start();
  }

}
