package Logica;


public class Movimiento {
    public enum TipoMovimiento {
        CELDA_A_COLUMNA,
        COLUMNA_A_CELDA,
        COLUMNA_A_FUNDACION,
        CELDA_A_FUNDACION,
        ENTRE_COLUMNAS
    }

    private Object origen;
    private Object destino;
    private Carta cartaMovida;
    private TipoMovimiento tipo;

    public Movimiento(Object origen, Object destino, Carta cartaMovida, TipoMovimiento tipo) {
        this.origen = origen;
        this.destino = destino;
        this.cartaMovida = cartaMovida;
        this.tipo = tipo;
    }

    public void revertir() {
        switch (tipo) {
            case CELDA_A_COLUMNA -> {
                ((Columna) destino).removerUltimaCarta();
                ((CeldaLibre) origen).agregar(cartaMovida);
            }
            case COLUMNA_A_CELDA -> {
                ((CeldaLibre) destino).remover(((CeldaLibre) destino).contarCartas() - 1);
                ((Columna) origen).agregarCarta(cartaMovida);
            }
            case COLUMNA_A_FUNDACION -> {
                ((Fundacion) destino).getCartas().eliminaFinal();
                ((Columna) origen).agregarCarta(cartaMovida);
            }
            case CELDA_A_FUNDACION -> {
                ((Fundacion) destino).getCartas().eliminaFinal();
                ((CeldaLibre) origen).agregar(cartaMovida);
            }
            case ENTRE_COLUMNAS -> {
                ((Columna) destino).removerUltimaCarta();
                ((Columna) origen).agregarCarta(cartaMovida);
            }
        }
    }

    public Object getOrigen() {
        return origen;
    }

    public Object getDestino() {
        return destino;
    }

    public Carta getCartaMovida() {
        return cartaMovida;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }
}
