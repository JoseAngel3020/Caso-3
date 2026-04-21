import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        int ni = 0, baseEventos = 0, nc = 0, ns = 0, tam1 = 0, tam2 = 0;

        // Leer CSV
        try (BufferedReader br = new BufferedReader(new FileReader("parametros.csv"))) {
            br.readLine();

            String linea;
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
        }

        // Buzones
        BuzonEntrada buzonEntrada = new BuzonEntrada();
        BuzonAlerta buzonAlerta = new BuzonAlerta();
        BuzonClasificacion buzonClasificacion = new BuzonClasificacion(tam1);

        BuzonConsolidacion[] buzonesServidores = new BuzonConsolidacion[ns];
        for (int i = 0; i < ns; i++) {
            buzonesServidores[i] = new BuzonConsolidacion(tam2);
        }

        // Sensores
        List<Sensor> sensores = new ArrayList<>();
        int totalEventos = 0;

        Sensor.configurarClase(100, ns);

        for (int i = 0; i < ni; i++) {
            int eventos = baseEventos * (i + 1);
            totalEventos += eventos;

            sensores.add(new Sensor(i + 1, buzonEntrada));
        }

        // Broker
        Broker broker = new Broker(1, totalEventos, ni, buzonEntrada, buzonAlerta, buzonClasificacion);

        // Administrador
        Administrador administrador = new Administrador(1,buzonAlerta, buzonClasificacion);

        // Clasificadores
        List<Clasificador> clasificadores = new ArrayList<>();
        for (int i = 0; i < nc; i++) {
            clasificadores.add(new Clasificador(buzonClasificacion, buzonesServidores, nc, ns));
        }

        // Servidores
        List<Servidor> servidores = new ArrayList<>();
        for (int i = 0; i < ns; i++) {
            servidores.add(new Servidor(i, buzonesServidores[i]));
        }

        // Start
        sensores.forEach(Thread::start);
        broker.start();
        administrador.start();
        clasificadores.forEach(Thread::start);
        servidores.forEach(Thread::start);

        // Join
        try {
            for (Sensor s : sensores) s.join();
            broker.join();
            administrador.join();
            for (Clasificador c : clasificadores) c.join();
            for (Servidor s : servidores) s.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Simulación terminada.");
    }
}