package tareas;

import java.util.Random;

public class EtapaPago {
  private Registros registros;
  private static final int DURACION_ITERACION = 30; // 40 milisegundos

  public EtapaPago(Registros registros) {
    this.registros = registros;
  }

  private class ThreadPago implements Runnable {
    public void run() {
      Random random = new Random();
      
      while (true) {
        if (noMoreSeats()) {
          try {
            Thread.sleep(random.nextInt(100, 300)); // 2-4 sg
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          if (noMoreSeats()) {
            System.out.println("---------$$$$$$ Todos los pagos completos $$$$$$---------");
            break;
          }
        }
    
        Asiento randomAsiento = registros.get_reserva(0);
        int randomNumber = random.nextInt(100);
        
        synchronized (randomAsiento) {
          if (registros.eliminar_reserva(0, randomAsiento)) { // Se elimina de la lista de pendientes
            if (randomNumber < 90) {
              randomAsiento.setEstado(1);
              registros.registrar_reserva(2, randomAsiento); // Se agrega a la lista de reservas confirmadas
            } else {
              randomAsiento.setEstado(-1);
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
