package tareas;

import tareas.Registros;
import java.util.Random;

public class EtapaValidacion {
  private Registros registros;
  private static final int DURACION_ITERACION = 50;

  public EtapaValidacion(Registros registros) {
    this.registros = registros;
  }

  // Define a thread that checks confirmed reservations, marking them as checked
  // or canceling them randomly
  private class ThreadValid implements Runnable {
    @Override
    public void run() {
      // Create a random object for generating random numbers
      Random random = new Random();
      // Loop indefinitely
      while (true) {
        if (registros.getConfirmadas_size() == 0) {
          // Wait for a random amount of time before trying again
          try {
            Thread.sleep(random.nextInt(5000)); // Wait up to 1 second
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          // Exit the loop if there are no more confirmed reservations
          if (registros.getConfirmadas_size() == 0) {
            break;
          }
        }
        // Takes a random reservation from the confirmed list
        Asiento randomAsiento = registros.get_reserva(2);
        // Generate a random number between 0 and 99
        int randomNumber = random.nextInt(100);
        // Synchronize on the randomAsiento to avoid conflicts with other threads
        synchronized (randomAsiento) {
          if (randomNumber < 90 && randomAsiento.getEstadoReserva() == 2) {
            // Mark the reservation as checked
            randomAsiento.setChecked();
          } else {
            // Cancel the reservation: Set the seat as discarded, add to the canceled
            // reservations list, and remove from confirmed list
            randomAsiento.cancelarReserva();
            registros.registrar_reserva(1, randomAsiento);
            registros.eliminar_reserva(2, randomAsiento);
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
    t1.start();
    t2.start();
  }

}
