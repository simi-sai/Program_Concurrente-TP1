package tareas;

import tareas.Registros;
import tareas.Asiento;
import java.util.Random;

public class EtapaReserva {
  private Asiento[][] asientos = new Asiento[31][6];
  private Registros registros = new Registros(); // Initialize here directly

  // This class represents a thread that can reserve a seat
  private class ThreadReserva implements Runnable {
    @Override
    public void run() {
      // Get a random Asiento object from the array
      Random random = new Random();
      int randomRow = random.nextInt(31);
      int randomColumn = random.nextInt(6);
      Asiento randomAsiento = asientos[randomRow][randomColumn];
      while (randomAsiento.getEstado() != 0) {
        randomRow = random.nextInt(31);
        randomColumn = random.nextInt(6);
        randomAsiento = asientos[randomRow][randomColumn];
      }
      randomAsiento.reservar();
      if (registros != null) { // Check if registros is not null
        registros.registrar_reserva(0, randomAsiento);
      }
    }
  }

  public void ejecutarEtapa() {
    // Initialize all seats
    for (int i = 0; i < 31; i++) { // Loop through rows
      for (int j = 0; j < 6; j++) { // Loop through columns
        asientos[i][j] = new Asiento(i, j);
      }
    }
    Thread thread1 = new Thread(new ThreadReserva());
    Thread thread2 = new Thread(new ThreadReserva());
    Thread thread3 = new Thread(new ThreadReserva());
    thread1.start();
    thread2.start();
    thread3.start();
  }
}
