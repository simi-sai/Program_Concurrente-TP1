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
    int totalVerif, totalCanceladas, totalReservas, randomNum;
    Random random = new Random();
    while (true) {
      totalVerif = registros.getVerificadas_size();
      totalCanceladas = registros.getCanceladas_size();
      totalReservas = totalVerif + totalCanceladas;
      randomNum = random.nextInt(500);
      if (totalReservas == 186) {
        try {
          Thread.sleep(randomNum); // Wait up to 1 second
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        totalVerif = registros.getVerificadas_size();
        totalCanceladas = registros.getCanceladas_size();
        totalReservas = totalVerif + totalCanceladas;
        if (totalReservas == 186) {
          break;
        }
      }
      try {
        FileWriter fw = new FileWriter("statistics.txt", true); // Append to the file
        PrintWriter writer = new PrintWriter(fw);
        writer.printf("Reservations Verified: %d, Cancelled: %d, Total: %d\n",
            totalVerif, totalCanceladas,
            totalReservas);
        writer.println("------------------"); // Write a line to the file
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
