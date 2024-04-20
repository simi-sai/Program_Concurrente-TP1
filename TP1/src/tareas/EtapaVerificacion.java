package tareas;

public class EtapaVerificacion {
  private Registros registros;
  private static final int DURACION_ITERACION = 100;

  public EtapaVerificacion(Registros registros) {
    this.registros = registros;
  }

  private class ThreadVerif implements Runnable {
    @Override
    public void run() {
      Random random = new Random();
      while (true) {
        if (registros.getConfirmadas_size() == 0) {
          // Wait for a random amount of time before trying again
          try {
            Thread.sleep(random.nextInt(2000, 4000)); // 2-4 sg
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          // Exit the loop if there are no more confirmed reservations
          if (registros.getConfirmadas_size() == 0) {
            break;
          }
        }
        Asiento randomAsiento = registros.get_reserva(2);

        // Synchronize on the randomAsiento to avoid conflicts with other threads
        synchronized (randomAsiento) {
          if (randomAsiento.getChecked() == 1) {
            // Perform the verification
            randomAsiento.verificarReserva();
            registros.registrar_reserva(3, randomAsiento);
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
    Thread t1 = new Thread(new ThreadVerif());
    Thread t2 = new Thread(new ThreadVerif());
    t1.start();
    t2.start();
  }

}
