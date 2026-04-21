public class Clasificador extends Thread {

    private BuzonClasificacion buzonClasificacion;
    private BuzonConsolidacion[] buzonesServidores;

    private int totalClasificadores;
    private int totalServidores;

    private static int terminados = 0;

    public Clasificador(BuzonClasificacion buzonClasificacion,
                        BuzonConsolidacion[] buzonesServidores,
                        int totalClasificadores,
                        int totalServidores) {

        this.buzonClasificacion = buzonClasificacion;
        this.buzonesServidores = buzonesServidores;
        this.totalClasificadores = totalClasificadores;
        this.totalServidores = totalServidores;
    }

    @Override
    public void run() {
        while (true) {
            Evento evento = buzonClasificacion.sacarEvento();

            // Evento de fin
            if (evento.esFin()) {
                manejarFin();
                break;
            }

            procesarEvento(evento);
        }
    }

    private void procesarEvento(Evento evento) {
        int tipo = evento.getTipo(); // valor entre 1 y ns
        int indiceServidor = tipo - 1;

        buzonesServidores[indiceServidor].agregarEvento(evento);
    }

    private void manejarFin() {
        synchronized (Clasificador.class) {
            terminados++;

            if (terminados == totalClasificadores) {
                // Último clasificador
                for (int i = 0; i < totalServidores; i++) {
                    buzonesServidores[i].agregarEvento(Evento.fin());
                }
            }
        }
    }
}