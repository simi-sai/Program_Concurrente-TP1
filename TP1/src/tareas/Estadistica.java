package tareas;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Estadistica implements Runnable {
  private Registros registros;

  public Estadistica(Registros registros) {
    this.registros = registros;
  }

  @Override
  public void run() {
    int totalVerif, totalCanceladas, totalReservas;
    double porcentajeVuelo = 0;
    while (true) {
      try {
        FileWriter fw = new FileWriter("statistics.txt", true); // Append to the file
        PrintWriter writer = new PrintWriter(fw);
        totalVerif = registros.getVerificadas_size();
        totalCanceladas = registros.getCanceladas_size();
        totalReservas = totalVerif + totalCanceladas;
        porcentajeVuelo = (totalVerif * 100) / 186;
        writer.printf("Reservations Verified: %d, Cancelled: %d, Total: %d\n",
            totalVerif, totalCanceladas,
            totalReservas);
        writer.println("------------------"); // Write a line to the file
        writer.flush();
        writer.close(); // Close the writer
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (allSeatsVerified()) {
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("The statistics are ready");
        System.out.println("Percentage reservations: " + porcentajeVuelo + "%");
        System.out.flush();
        break;
      }
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private boolean allSeatsVerified() {
    int totalVerif = registros.getVerificadas_size();
    int totalCanceladas = registros.getCanceladas_size();
    int totalReservas = totalVerif + totalCanceladas;
    if (totalReservas == 186) {
      return true;
    } else {
      return false;
    }
  }
}
