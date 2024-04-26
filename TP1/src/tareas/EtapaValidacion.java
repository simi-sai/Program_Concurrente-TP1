package tareas;

import java.util.Random;

public class EtapaValidacion {
  private Registros registros;
  private static final int DURACION_ITERACION = 20;

  public EtapaValidacion(Registros registros) {
    this.registros = registros;
  }

  private class ThreadValid implements Runnable {
    public void run() {
      Random random = new Random();
      
      while (true) {
        if (noMoreSeats()) {
          try {
            Thread.sleep(random.nextInt(100, 300)); // 2-4 sg
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          
          if (registros.getConfirmadas_size() == 0) {
            System.out.println("--------- Thread validacion: finished ---------");
            System.out.flush();
            break;
          }
        }
        
        Asiento randomAsiento = registros.get_reserva(2);
        int randomNumber = random.nextInt(100);
        
        synchronized (randomAsiento) {
          if (randomAsiento.getChecked() == false) {
            if (randomNumber < 90) {
              randomAsiento.setChecked();
            } else {
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

  private boolean noMoreSeats() {
    if (registros.getConfirmadas_size() == 0) {
      return true;
    }
    return false; // All seats are reserved
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
