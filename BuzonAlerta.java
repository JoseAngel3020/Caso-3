import java.util.LinkedList;
import java.util.Queue;

public class BuzonAlerta {
    private Queue<Evento> eventos;

        public BuzonAlerta() {
            this.eventos = new LinkedList<>();
        }
    public synchronized void agregarEvento(Evento evento) {
        eventos.add(evento);
    }


    public synchronized boolean estaVacio() {
        return eventos.isEmpty();
    }

    public synchronized Evento sacarEvento() {

        return eventos.poll();
    }

    
}
