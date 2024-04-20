package tareas;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import tareas.Registros;

public class Estadistica implements Runnable {
  private Registros registros;

  public Estadistica(Registros registros) {
    this.registros = registros;
  }

  @Override
  public void run() {
    int totalVerif, totalCanceladas, totalReservas;
    while (true) {
      if (allSeatsVerified()) {
        try {
          FileWriter fw = new FileWriter("statistics.txt", true); // Append to the file
          PrintWriter writer = new PrintWriter(fw);
          totalVerif = registros.getVerificadas_size();
          totalCanceladas = registros.getCanceladas_size();
          totalReservas = totalVerif + totalCanceladas;
          // TODO: Imprimir ocupacion final del vuelo
          // TODO: Analisis estadistico dps de multiples ejecuciones con conclusiones
          writer.printf("Reservations Verified: %d, Cancelled: %d, Total: %d\n",
              totalVerif, totalCanceladas,
              totalReservas);
          writer.println("------------------"); // Write a line to the file
          writer.flush();
          writer.close(); // Close the writer
        } catch (IOException e) {
          e.printStackTrace();
        }
        break;
      } else {
        try {
          FileWriter fw = new FileWriter("statistics.txt", true); // Append to the file
          PrintWriter writer = new PrintWriter(fw);
          totalVerif = registros.getVerificadas_size();
          totalCanceladas = registros.getCanceladas_size();
          totalReservas = totalVerif + totalCanceladas;
          writer.printf("Reservations Verified: %d, Cancelled: %d, Total: %d\n",
              totalVerif, totalCanceladas,
              totalReservas);
          writer.println("------------------"); // Write a line to the file
          writer.flush();
          writer.close(); // Close the writer
        } catch (IOException e) {
          e.printStackTrace();
        }
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
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
