/*
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
    
}
