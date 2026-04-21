import java.util.concurrent.ThreadLocalRandom;

public class Broker extends Thread {

    private int id; 
    private BuzonEntrada buzonEntradaEventos;
    private final int totalEventos;

    private BuzonAlerta buzonAlertas;
    private BuzonClasificacion buzonClasificacion;

    public Broker(int id, int ne, int ni, BuzonEntrada buzonEntradaEventos , BuzonAlerta buzonAlertas, BuzonClasificacion buzonClasificacion) {
        this.id = id;
        this.totalEventos = ne * (ni * (ni + 1) / 2);
        this.buzonEntradaEventos = buzonEntradaEventos;
        this.buzonAlertas = buzonAlertas;
        this.buzonClasificacion = buzonClasificacion;
    }

    public void ProcesarEvento(Evento evento) {
       if (esAnomalo()) {
    
            buzonAlertas.agregarEvento(evento);

        } else {
            
            while (buzonClasificacion.estaLleno()) {
                Thread.yield();
            }
            buzonClasificacion.agregarEvento(evento);
        }
    }

    public boolean esAnomalo() {
        int numero =ThreadLocalRandom.current().nextInt(0, 200);
        return numero % 8 == 0;
    }

    @Override
    public void run() {
        int eventosProcessados = 0;

        while (eventosProcessados < totalEventos) {
            
            while (buzonEntradaEventos.estaVacio()) {
                try {
                    wait();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            Evento evento = buzonEntradaEventos.sacarEvento();
            ProcesarEvento(evento);
            eventosProcessados++;
        }
        System.out.println("=== BROKER: " + id + " termino su ejecuccion ===");
        
    }
}



