package tareas;

import java.util.Random;

public class EtapaValidacion {
  private Registros registros;
  private static final int DURACION_ITERACION = 200;

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
            Thread.sleep(random.nextInt(1000, 3000)); // 2-4 sg
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
        // Generate a random number between 0 and 99
        int randomNumber = random.nextInt(100);
        // Checks if the randomAsiento != (which means that there is a confirmed
        // reservation) and if it wasn't already checked
        if (randomAsiento != null && (randomAsiento.getChecked() == 0)) {
          // Synchronize on the randomAsiento to avoid conflicts with other threads
          synchronized (randomAsiento) {
            if (randomNumber < 90) {
              // Mark the reservation as checked
              randomAsiento.setChecked();
            } else {
              // Cancel the reservation: Set the seat as discarded, add to the canceled
              // reservations list, and remove from confirmed list
              registros.eliminar_reserva(2, randomAsiento);
              randomAsiento.cancelarReserva();
              registros.registrar_reserva(1, randomAsiento);
            }
          }
        } else {
          continue;
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
