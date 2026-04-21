import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BuzonConsolidacion {
    private BlockingQueue<Evento> eventos;

    public BuzonConsolidacion(int tam) {
        this.eventos = new ArrayBlockingQueue<>(tam);
    }

    public synchronized void agregarEvento(Evento evento) {
        eventos.add(evento);
    }

    public synchronized boolean estaLleno() {
        return eventos.remainingCapacity() == 0;
    }

    public synchronized boolean estaVacio() {
        return eventos.isEmpty();
    }

    public synchronized Evento sacarEvento() {

        return eventos.poll();
    }

    public synchronized void descartarEvento(Evento evento) {
        eventos.remove(evento);
    }
}