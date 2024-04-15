package tareas;

import java.util.Random;

public class EtapaPago {
  private Registros registros;

  public EtapaPago(Registros registros) {
    this.registros = registros;
  }

  // Define a thread that processes pending reservations, approves or rejects them
  // randomly
  private class ThreadPago implements Runnable {
    @Override
    public void run() {
      // Create a random object for generating random numbers
      Random random = new Random();
      // Loop indefinitely
      while (true) {
        // Takes a random reservation from the pending list
        Asiento randomAsiento = registros.get_reserva(0);
        int randomNumber = random.nextInt(100);
        if (randomAsiento == null) {
          break;
        }
        // Synchronize on the randomAsiento to avoid conflicts with other threads
        synchronized (randomAsiento) {
          if (randomAsiento.getEstadoReserva() == 1) {
            if (randomNumber < 90) {
              // Aprobado
              randomAsiento.confirmarReserva();
              registros.registrar_reserva(2, randomAsiento); // Se agrega a la lista de reservas confirmadas
              registros.eliminar_reserva(0, randomAsiento); // Se elimina de la lista de pendientes
            } else {
              randomAsiento.cancelarReserva();
              registros.registrar_reserva(1, randomAsiento); // Se agrega a la lista de reservas canceladas
              registros.eliminar_reserva(0, randomAsiento); // Se elimina de la lista de pendientes
            }
          }
        }
      }
    }
  }

  public void ejecutarEtapa() {
    Thread thread1 = new Thread(new ThreadPago());
    Thread thread2 = new Thread(new ThreadPago());
    thread1.start();
    thread2.start();
  }

}
