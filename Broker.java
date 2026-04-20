/*
o Lee los eventos uno por uno y determina si son normales o representan alguna
anomalía en el comportamiento esperado.
o En este caso vamos a simular la presencia de eventos anómalos con base en un
número generado seudoaleatoriamente. Por cada evento revisado, el bróker
genera un número entre 0 y 200, si el número es múltiplo de 8, el evento es
considerado anómalo y enviado al buzón de alertas, si no el evento es
considerado normal y enviado al buzón para clasificación.
o Cuando todos los eventos esperados han sido generados por los sensores, el
bróker envía un evento de fin al administrador. Esto significa que el bróker debe
conocer el número total de eventos esperados. 
 */
public class Broker extends Thread {
    
}
