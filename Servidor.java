/*
o Cada servidor debe procesar los eventos normales que va tomando de su buzón
de consolidación.
o La lectura de eventos se hace uno por uno. Para simular la lectura y el
procesamiento, al recibir un evento lo procesan durante un tiempo aleatorio (entre
100 ms y 1000 ms) y luego quedan a la espera del siguiente evento.
o Termina su ejecución cuando recibe un evento de fin
 */
import java.util.Random;

public class Servidor extends Thread {

    private int id;
    private BuzonConsolidacion buzonConsolidacion;
    private Random random;

    public Servidor(int id, Buzon buzon) {
        this.id = id;
        this.buzonConsolidacion = buzonConsolidacion;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (true) {
            Evento evento = buzonConsolidacion.retirarEvento();

            // Verificar fin
            if (evento.esFin()) {
                System.out.println("Servidor " + id + " termina.");
                break;
            }

            procesarEvento(evento);
        }
    }

    private void procesarEvento(Evento evento) {
        try {
            int tiempo = 100 + random.nextInt(901); // 100 a 1000 ms

            System.out.println("Servidor " + id +
                " procesando evento tipo " + evento.getTipo() +
                " durante " + tiempo + " ms");

            Thread.sleep(tiempo);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}