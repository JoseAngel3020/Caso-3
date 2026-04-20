/*
o Cada Sensor (Thread productor) se inicia con un número fijo de eventos que debe
generar. Este número se calcula con un valor base en el archivo de configuración
multiplicado por el número de identificación del sensor (usaremos identificadores
secuenciales, empezando en 1).
o Cada sensor genera sus eventos con un identificador único. El identificador debe
generarse con el identificador del sensor y un secuencial.
o Cada evento debe incluir, adicionalmente, un número seudoaleatorio entre 1 y ns,
donde ns es el número de servidores de consolidación y despliegue. Este valor
indica dos cosas: el tipo del evento y el servidor al que el evento debe llegar.
o Los sensores depositan sus eventos en un buzón con capacidad ilimitada.
o Cada sensor termina su ejecución cuando termina de producir el número asignado
de eventos.
 */
public class Sensor extends Thread {

}