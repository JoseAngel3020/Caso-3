
import java.util.concurrent.ThreadLocalRandom;

public class Administrador extends Thread {
    private int id;
    private BuzonAlerta buzonAlertas;
    private BuzonClasificacion buzonClasificacion;

    public Administrador(int id, BuzonAlerta buzonAlertas, BuzonClasificacion buzonClasificacion) {
        this.id = id;
        this.buzonAlertas = buzonAlertas;
        this.buzonClasificacion = buzonClasificacion;
    }



    public void EnviarEvento(Evento evento) {

        if(esNormal()){

            while (buzonClasificacion.estaLleno()) {
                Thread.yield();
            }
            buzonClasificacion.agregarEvento(evento);
        }
        //si no cumple la condicion se descarta, como se hace .poll para sacarlo de la cola no es necesario hacer nada
       
    }

    private boolean esNormal() {
        int numero =ThreadLocalRandom.current().nextInt(0, 20);
        return numero % 4 == 0;
    }

    @Override
    public void run() {

        boolean condicionSalida = true;

        while (condicionSalida) {

            while (buzonAlertas.estaVacio()) {
                Thread.yield();
            }
            Evento evento = buzonAlertas.sacarEvento();

            if (evento.check()) {
                condicionSalida = false;
            } else  {
                EnviarEvento(evento);   
            }  
        }
         System.out.println("Administrador " + id + " terminó.");
    }
    
}
