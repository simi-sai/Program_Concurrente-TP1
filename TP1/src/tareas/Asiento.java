package tareas;

public class Asiento {
  // Atributos
  private int estado;
  private int fila;
  private int columna;
  private boolean checked;
  // private EstadoReserva estadoReserva;

  // private enum EstadoReserva {
  // DISPONIBLE, PENDIENTE, CONFIRMADA, CANCELADA, VERIFICADA;
  // }

  // Constructor
  public Asiento(int f, int c) {
    this.estado = 0; // 0: Libre // 1: Ocupado // -1: Descartado
    this.fila = f;
    this.columna = c;
    this.checked = false;
    // this.estadoReserva = EstadoReserva.DISPONIBLE;
  }

  public synchronized int getEstado() {
    return estado;
  }

  public void setEstado(int estado) {
    this.estado = estado;
  }

  // public void reservar() {
  // if (estadoReserva == EstadoReserva.DISPONIBLE) {
  // setEstado(1);
  // estadoReserva = EstadoReserva.PENDIENTE;
  // }
  // }

  public int getFila() {
    return fila;
  }

  public int getColumna() {
    return columna;
  }

  // public void confirmarReserva() {
  // if (estadoReserva == EstadoReserva.PENDIENTE) {
  // estadoReserva = EstadoReserva.CONFIRMADA;
  // }
  // }

  // public void cancelarReserva() {
  // if (estadoReserva == EstadoReserva.PENDIENTE || estadoReserva ==
  // EstadoReserva.CONFIRMADA) {
  // setEstado(-1);
  // estadoReserva = EstadoReserva.CANCELADA;
  // }
  // }

  // public void verificarReserva() {
  // if (estadoReserva == EstadoReserva.CONFIRMADA) {
  // estadoReserva = EstadoReserva.VERIFICADA;
  // }
  // }

  // public int getEstadoReserva() {
  // return estadoReserva.ordinal();
  // }

  public boolean getChecked() {
    return checked;
  }

  public void setChecked() {
    this.checked = true;
  }
}
