import java.util.Random;

public class Servidor extends Thread {

    private int id;
    private BuzonConsolidacion buzonConsolidacion;
    private Random random;

    public Servidor(int id, BuzonConsolidacion buzonConsolidacion) {
        this.id = id;
        this.buzonConsolidacion = buzonConsolidacion;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (true) {
            Evento evento = buzonConsolidacion.sacarEvento();

            if (evento.esFin()) {
                System.out.println("Servidor " + id + " termina.");
                break;
            }

            procesarEvento(evento);
        }
    }

    private void procesarEvento(Evento evento) {
        try {
            int tiempo = 100 + random.nextInt(901);

            System.out.println("Servidor " + id +
                    " procesando evento tipo " + evento.getTipo() +
                    " durante " + tiempo + " ms");

            Thread.sleep(tiempo);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}