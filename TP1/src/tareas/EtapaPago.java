package tareas;

import java.util.Random;

public class EtapaPago {
  private Registros registros;
  private static final int DURACION_ITERACION = 200; // 40 milisegundos

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
        if (noMoreSeats()) {
          try {
            Thread.sleep(random.nextInt(2000, 5000)); // 2-4 sg
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
        // Synchronize on the randomAsiento to avoid conflicts with other threads
        synchronized (randomAsiento) {
          if (randomNumber < 90) {
            // Aprobado
            randomAsiento.confirmarReserva();
            registros.registrar_reserva(2, randomAsiento); // Se agrega a la lista de reservas confirmadas
          } else {
            randomAsiento.cancelarReserva();
            registros.registrar_reserva(1, randomAsiento); // Se agrega a la lista de reservas canceladas
          }
          registros.eliminar_reserva(0, randomAsiento); // Se elimina de la lista de pendientes
        }
        try {
          Thread.sleep(DURACION_ITERACION);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  // private boolean noMoreSeats() {
  // if (registros.getPendientes_size() == 0) {
  // return true;
  // }
  // return false;
  // }
  private boolean noMoreSeats() {
    for (int i = 0; i < 31; i++) {
      for (int j = 0; j < 6; j++) {
        if ((registros.getMatriz())[i][j].getEstadoReserva() == 1) {
          return false; // At least one seat is not reserved
        }
      }
    }
    return true; // All seats are reserved
  }

  public void ejecutarEtapa() {
    Thread thread1 = new Thread(new ThreadPago());
    Thread thread2 = new Thread(new ThreadPago());
    thread1.start();
    thread2.start();
  }

}
