import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BuzonClasificacion {
    private BlockingQueue<Evento> eventos;

    public BuzonClasificacion(int tam) {
        this.eventos = new ArrayBlockingQueue<>(tam);
    }

    //espera semi-activa
    public synchronized void agregarEvento(Evento evento) {
        eventos.add(evento);
    }

    public synchronized boolean estaLleno() {
        return eventos.remainingCapacity() == 0;
    }

    public synchronized boolean estaVacio() {
        return eventos.isEmpty();
    }

    //espera pasiva
    public synchronized Evento sacarEvento() {

        return eventos.poll();
    }

    public synchronized void descartarEvento(Evento evento) {
        eventos.remove(evento);
    }
}