package tareas;

import java.util.Random;

public class EtapaValidacion {
  private Registros registros;
  private static final int DURACION_ITERACION = 250;

  public EtapaValidacion(Registros registros) {
    this.registros = registros;
  }

  private class ThreadValid implements Runnable {
    Random random = new Random();

    public void run() {
      while (true) {
        if (noMoreConfirmadas()) {
          try {
            Thread.sleep(random.nextInt(2000, 2500));
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          
          if (noMoreConfirmadas()) {
            System.out.println("--------- Thread VALIDACION: finished ---------");
            System.out.flush();
            break;
          }
        }
        
        int randomNumber = random.nextInt(100);
        Asiento randomAsiento = registros.get_reserva(2);
        
        synchronized (randomAsiento) {
          if (!registros.getChecked(randomAsiento)) {
            if (randomNumber < 90) {
              registros.setChecked(randomAsiento);
            } else {
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

  private boolean noMoreConfirmadas() {
    return registros.getConfirmadas_size() == 0;
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
