/*
3. Clasificadores
o Leen los eventos del buzón de clasificación y dependiendo del tipo de
evento lo envían al servidor de consolidación y despliegue correspondiente.
o Un clasificador termina cuando recibe un evento de fin. 

o Cada clasificador retira eventos del buzón para clasificación, uno por uno,
identifica el tipo de evento, y envía el evento al buzón del servidor de
consolidación correspondiente.
o Cada clasificador termina cuando recibe un evento de fin.
o El último clasificador en terminar debe generar y enviar el evento de fin para los
servidores de consolidación y despliegue, para esto debe crear y depositar ns
eventos de fin; un evento en el buzón de cada servidor de consolidación.
o La condición previa significa que los clasificadores deben saber cuántos están
corriendo de forma concurrente y registrar su terminación de alguna forma. 
 */
public class Clasificador extends Thread {

    private BuzonClasificacion buzonClasificacion;
    private BuzonConsolidacion buzonConsolidacion;

    private int totalClasificadores;
    private int totalServidores;

    private static int terminados = 0;

    public Clasificador(BuzonClasificacion buzonClasificacion,
                        Buzon[] buzonesServidores,
                        int totalClasificadores,
                        int totalServidores) {

        this.buzonClasificacion = buzonClasificacion;
        this.buzonConsolidacion = buzonConsolidacion;
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
        // Se asume que el tipo es un número entre 1 y ns
        int tipo = evento.getTipo(); 
        int indiceServidor = tipo - 1;

        buzonConsolidacion.agregarEvento(evento);
    }

    private void manejarFin() {
        synchronized (Clasificador.class) {
            terminados++;

            if (terminados == totalClasificadores) {
                for (int i = 0; i < totalServidores; i++) {
                    buzonConsolidacion.agregarEvento(Evento.check());
                }
            }
        }
    }

    public synchronized Evento sacarEvento() {
        while (cola.isEmpty()) { 
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return cola.remove(0);
    }

    public synchronized void agregarEvento(Evento evento) {
        cola.add(evento);
        notify();
    }
}