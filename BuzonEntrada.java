import java.util.LinkedList;
import java.util.Queue;

public class BuzonEntrada {

    private Queue<Evento> eventos;

    public BuzonEntrada() {
        this.eventos = new LinkedList<>();
    }

    public synchronized void agregarEvento(Evento evento) {
        eventos.add(evento);
        notifyAll();
    }

    public synchronized boolean estaVacio() {
        return eventos.isEmpty();
    }

    public synchronized Evento sacarEvento() {

        return eventos.poll();
    }
}
