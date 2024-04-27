package tareas;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Estadistica implements Runnable {
  private Registros registros;

  public Estadistica(Registros registros) {
    this.registros = registros;
  }

  public void run() {
    int totalVerif, totalCanceladas, totalReservas, totalPendientes, totalConfirm;
    double porcentajeVuelo = 0;

    while (true) {
      try {
        FileWriter fw = new FileWriter("statistics.txt", true); // Append to the file
        PrintWriter writer = new PrintWriter(fw);
        totalPendientes = registros.getPendientes_size();
        totalVerif = registros.getVerificadas_size();
        totalCanceladas = registros.getCanceladas_size();
        totalConfirm = registros.getConfirmadas_size();
        totalReservas = totalVerif + totalCanceladas;
        porcentajeVuelo = (totalVerif * 100) / 186;
        System.out.printf(
        "Pending: %d, Confirmed: %d, Verified: %d, Cancelled: %d, Total: %d\n",
        totalPendientes, totalConfirm,
        totalVerif, totalCanceladas, totalReservas);
        System.out.flush();
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
          Thread.sleep(2000);
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
    int totalCanceladas = registros.getCanceladas_size();
    int totalVerif = registros.getVerificadas_size();

    return totalCanceladas + totalVerif >= 186;
  }
}
