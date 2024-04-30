package tareas;

import java.util.Random;

public class EtapaPago {
  private Registros registros;
  private static final int DURACION_ITERACION = 250;

  public EtapaPago(Registros registros) {
    this.registros = registros;
  }

  private class ThreadPago implements Runnable {
    Random random = new Random();

    public void run() {
      while (true) {
        if (noMorePendientes()) {
          try {
            Thread.sleep(random.nextInt(1000, 2000));
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          if (noMorePendientes()) {
            System.out.println("--------- Thread PAGO: finished ---------");
            break;
          }
        }
    
        int randomNumber = random.nextInt(100);
        Asiento randomAsiento = registros.get_reserva(0);
        
        synchronized (randomAsiento) {
          if (registros.eliminar_reserva(0, randomAsiento)) { // Se elimina de la lista de pendientes
            if (randomNumber < 90) {
              registros.registrar_reserva(2, randomAsiento); // Se agrega a la lista de reservas confirmadas
            } else {
              registros.registrar_reserva(1, randomAsiento); // Se agrega a la lista de reservas canceladas
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

  private boolean noMorePendientes() {
    return registros.getPendientes_size() == 0;
  }
  
  public void ejecutarEtapa() {
    Thread thread1 = new Thread(new ThreadPago());
    Thread thread2 = new Thread(new ThreadPago());
    thread1.start();
    thread2.start();
  }
}
