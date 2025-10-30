package Logica;

public class Movimiento {

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

    public enum TipoMovimiento {
        CELDA_A_COLUMNA,
        COLUMNA_A_CELDA,
        COLUMNA_A_FUNDACION,
        CELDA_A_FUNDACION,
        ENTRE_COLUMNAS
    }

    public void revertir() {
        switch (tipo) {
            case CELDA_A_COLUMNA:
                ((Columna) destino).removerUltimaCarta();
                ((CeldaLibre) origen).agregar(cartaMovida);
                break;
            case COLUMNA_A_CELDA:
                ((CeldaLibre) destino).remover(((CeldaLibre) destino).contarCartas() - 1);
                ((Columna) origen).agregarCarta(cartaMovida);
                break;
            case COLUMNA_A_FUNDACION:
                Fundacion fund = (Fundacion) destino;
                if (fund.verTope() != null && fund.verTope().equals(cartaMovida)) {
                    fund.retirarCima();
                    ((Columna) origen).agregarCarta(cartaMovida);
                } else {
                    System.out.println("No se puede revertir: la carta ya no está en la cima de la fundación.");
                }
                break;
            case CELDA_A_FUNDACION:
                Fundacion fundCelda = (Fundacion) destino;
                if (fundCelda.verTope() != null && fundCelda.verTope().equals(cartaMovida)) {
                    fundCelda.retirarCima();
                    ((CeldaLibre) origen).agregar(cartaMovida);
                } else {
                    System.out.println("No se puede revertir: la carta ya no está en la cima de la fundación.");
                }
                break;
            case ENTRE_COLUMNAS:
                ((Columna) destino).removerUltimaCarta();
                ((Columna) origen).agregarCarta(cartaMovida);
                break;
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
