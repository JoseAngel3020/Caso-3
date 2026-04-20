/*
o Lee los eventos sospechosos del buzón de alertas y determina si el
comportamiento anómalo es efectivamente un problema o solo un evento fuera de
lo común pero inofensivo.
o Vamos a simular la presencia de eventos sospechosos pero inofensivos por medio
de un número seudoaleatorio. Por cada evento analizado el administrador genera
un número entre 0 y 20. Si el número es múltiplo de 4, el evento es considerado
normal y enviado al buzón para clasificación, si no el evento es descartado. Tenga
en cuenta que en este caso estamos descartando las alertas confirmadas, pero en
un contexto real, el administrador y la organización deben manejar las alertas
confirmadas de forma apropiada.
o Cuando recibe el evento de fin termina. Pero antes de terminar, debe avisar a los
clasificadores que deben terminar, para esto debe crear y depositar nc eventos de
fin en el buzón para clasificación (uno por clasificador).
 */
public class Administrador extends Thread {
    
}
