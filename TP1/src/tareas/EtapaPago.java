package tareas;

import java.util.Random;

public class EtapaPago {
  private Registros registros;
  private static final int DURACION_ITERACION = 300; // 40 milisegundos

  public EtapaPago(Registros registros) {
    this.registros = registros;
  }

  // Define a thread that processes pending reservations, approves or rejects them
  // randomly
  private class ThreadPago implements Runnable {
    public void run() {
      // Create a random object for generating random numbers
      Random random = new Random();
      // Loop indefinitely
      while (true) {
        if (noMoreSeats()) {
          try {
            Thread.sleep(random.nextInt(1000, 3000)); // 2-4 sg
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          if (noMoreSeats()) {
            System.out.println("---------$$$$$$ Todos los pagos completos $$$$$$---------");
            break;
          }
        }
        // Takes a random reservation from the pending list
        Asiento randomAsiento = registros.get_reserva(0);
        int randomNumber = random.nextInt(100);
        if (randomAsiento != null) {
          // Synchronize on the randomAsiento to avoid conflicts with other threads
          synchronized (randomAsiento) {
            registros.eliminar_reserva(0, randomAsiento); // Se elimina de la lista de pendientes
            if (randomNumber < 90) {
              // Aprobado
              randomAsiento.setEstado(1);
              registros.registrar_reserva(2, randomAsiento); // Se agrega a la lista de reservas confirmadas
            } else {
              randomAsiento.setEstado(-1);
              registros.registrar_reserva(1, randomAsiento); // Se agrega a la lista de reservas canceladas
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

  private boolean noMoreSeats() {
    if (registros.getPendientes_size() == 0) {
      return true;
    }
    return false; // All seats are reserved
  }

  public void ejecutarEtapa() {
    Thread thread1 = new Thread(new ThreadPago());
    Thread thread2 = new Thread(new ThreadPago());
    thread1.start();
    thread2.start();
  }

}
