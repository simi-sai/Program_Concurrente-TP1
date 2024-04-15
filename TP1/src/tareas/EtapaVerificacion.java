package tareas;

public class EtapaVerificacion {
  private Registros registros;

  public EtapaVerificacion(Registros registros) {
    this.registros = registros;
  }

  private class ThreadVerif implements Runnable {
    @Override
    public void run() {
      // Create a random object for generating random numbers
      // Loop indefinitely
      while (true) {
        // Takes a random reservation from the confirmed list
        Asiento randomAsiento = registros.get_reserva(2);
        // Exit the loop if there are no more confirmed reservations
        if (randomAsiento == null) {
          break;
        }
        // Synchronize on the randomAsiento to avoid conflicts with other threads
        synchronized (randomAsiento) {
          if (randomAsiento.getChecked() == 1) {
            randomAsiento.verificarReserva();
            registros.registrar_reserva(3, randomAsiento);
            registros.eliminar_reserva(2, randomAsiento);
          }
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
