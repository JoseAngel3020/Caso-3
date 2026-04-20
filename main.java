import java.io.*;
import java.util.*;

public class main {
    public static void main(String[] args) throws IOException {
        //Parametros
        int ni = 0;
        int baseEventos = 0;
        int nc = 0;
        int ns = 0;
        int tam1 = 0;
        int tam2 = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("parametros.csv"))) {
            String linea = br.readLine(); // saltar encabezado

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                String clave = partes[0].trim();
                int valor = Integer.parseInt(partes[1].trim());

                switch (clave) {
                    case "ni" -> ni = valor;
                    case "baseEventos" -> baseEventos = valor;
                    case "nc" -> nc = valor;
                    case "ns" -> ns = valor;
                    case "tam1" -> tam1 = valor;
                    case "tam2" -> tam2 = valor;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            }
        //Buzones
        BuzonClasificacion buzonClasificacion = new BuzonClasificacion(baseEventos);
        BuzonConsolidacion buzonConsolidacion = new BuzonConsolidacion(ns);
        BuzonEntrada buzonEntrada = new BuzonEntrada(ni);
        BuzonAlerta buzonAlerta = new BuzonAlerta();
        //Buzones de consolidacion para servidores
        BuzonConsolidacion[] buzonesServidores = new BuzonConsolidacion[ns];
        for (int i = 0; i < ns; i++) {
            buzonesServidores[i] = new BuzonConsolidacion(tam1);
        }
        //Sensores
        List<Sensor> sensores = new ArrayList<>();
        for (int i = 0; i < ni; i++) {
            sensores.add(new Sensor(i, buzonEntrada));
        }
        //Broker
        Broker broker = new Broker(buzonEntrada, buzonClasificacion);
        //Administrador
        Administrador administrador = new Administrador(buzonAlerta);
        //Clasificadores
        List<Clasificador> clasificadores = new ArrayList<>();
        for (int i = 0; i < nc; i++) {
            clasificadores.add(new Clasificador(buzonClasificacion, buzonesServidores, nc, ns));
        }
        //Servidores de consolidacion
        List<Servidor> servidoresConsolidacion = new ArrayList<>();
        for (int i = 0; i < ns; i++) {
            servidoresConsolidacion.add(new Servidor(buzonesServidores[i]));
        }
        //Iniciar hilos
        broker.start();
        administrador.start();
        for (Sensor sensor : sensores) {
            sensor.start();
        }
        for (Clasificador clasificador : clasificadores) {
            clasificador.start();
        }
        for (Servidor servidor : servidoresConsolidacion) {
            servidor.start();
        }
        // Esperar a que terminen los hilos
        try {
            for (Sensor sensor : sensores) {
                sensor.join();
            }
            broker.join();
            for (Clasificador clasificador : clasificadores) {
                clasificador.join();
            }
            for (Servidor servidor : servidoresConsolidacion) {
                servidor.join();
            }
            administrador.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
    }
}
