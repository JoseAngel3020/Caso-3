import java.util.concurrent.ThreadLocalRandom;

public class Sensor extends Thread {

    //atributos
    private final int id;
    private final int numEventos;
    //referencia a buzon
    private BuzonEntrada buzonEntradaEventos;
    //atributo para configurar el numero de eventos a generar
    private static int numBaseEventos ; // configurable
    private static int numServidores; // configurable


    public Sensor(int id, BuzonEntrada buzonEntradaEventos) {
        this.id = id;
        this.numEventos =  numBaseEventos* id;
        this.buzonEntradaEventos = buzonEntradaEventos;
    }

    //configura valores para uso de creacion de sensores y eventos
    public void configurarClase(int numBase, int numServidores) {
        Sensor.numBaseEventos = numBase;
        Sensor.numServidores = numServidores;
    }

    @Override
    public void run() {

        for (int i = 0; i < numEventos; i++) {

            int eventoId = id * 1000 + i; 
            int tipoServidor = ThreadLocalRandom.current().nextInt(1, numServidores);
            Evento evento = new Evento(eventoId, tipoServidor);
            buzonEntradaEventos.agregarEvento(evento);
        }
          System.out.println("=== SENSOR: " + id + " termino de generar eventos ===");
        
        
    }
    
}