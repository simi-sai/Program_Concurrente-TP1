package tareas;

import java.util.Random;

public class EtapaVerificacion {
  private Registros registros;
  private static final int DURACION_ITERACION = 100;

  public EtapaVerificacion(Registros registros) {
    this.registros = registros;
  }

  private class ThreadVerif implements Runnable {
    Random random = new Random();

    public void run() {
      while (true) {
        if (noMoreConfirmadas()) {
          try {
            Thread.sleep(random.nextInt(2000, 3000));
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          
          if (noMoreConfirmadas()) {
            System.out.println("--------- Thread VERIFICACION: finished --------- ");
            System.out.flush();
            break;
          }
        }

        Asiento randomAsiento = registros.get_reserva(2);

        synchronized (randomAsiento) {
          if (registros.getChecked(randomAsiento)) {
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

  private boolean noMoreConfirmadas() {
    return registros.getConfirmadas_size() == 0;
  }

  public void ejecutarEtapa() {
    Thread t1 = new Thread(new ThreadVerif());
    Thread t2 = new Thread(new ThreadVerif());
    t1.start();
    t2.start();
  }
}
