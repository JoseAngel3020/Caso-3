public class Evento {

    private int eventoId;
    private int tipoServidor;


    public Evento(int eventoId, int tipoServidor) {
        this.eventoId = eventoId;
        this.tipoServidor = tipoServidor;
    }

    // revisa si el evento es de salida (cuando -1)
    public boolean check() {
        return tipoServidor == -1; 
    }

    public int getId() {
        return eventoId;
    }

    public int getTipoServidor() {
        return tipoServidor;
    }


    
}
