package Logica;


public class EightOffGame {
    private Baraja baraja;
    private Columna[] columnas;
    private CeldaLibre celdas;
    private Fundacion[] fundaciones;
    private Lista<Movimiento> historial;

    public EightOffGame() {
        baraja = new Baraja();
        columnas = new Columna[8];
        for (int i = 0; i < 8; i++) {
            columnas[i] = new Columna();
        }
        celdas = new CeldaLibre();
        fundaciones = new Fundacion[4];
        for (int i = 0; i < 4; i++) {
            fundaciones[i] = new Fundacion();
        }
        historial = new Lista<>();
        repartirCartas();
    }

    private void repartirCartas() {
        int columnaActual = 0;
        while (!baraja.estaVacia()) {
            Carta carta = baraja.sacarCarta();
            columnas[columnaActual].agregarCarta(carta);
            columnaActual = (columnaActual + 1) % 8;
        }
    }

    public boolean moverColumnaACelda(int columnaIndex) {
        Columna origen = columnas[columnaIndex];
        Carta carta = origen.verUltimaCarta();
        if (carta != null && celdas.agregar(carta)) {
            origen.removerUltimaCarta();
            historial.insertaFinal(new Movimiento(origen, celdas, carta, Movimiento.TipoMovimiento.COLUMNA_A_CELDA));
            return true;
        }
        return false;
    }

    public boolean moverCeldaAColumna(int celdaIndex, int columnaIndex) {
        Carta carta = celdas.ver(celdaIndex);
        if (carta != null) {
            columnas[columnaIndex].agregarCarta(carta);
            celdas.remover(celdaIndex);
            historial.insertaFinal(new Movimiento(celdas, columnas[columnaIndex], carta, Movimiento.TipoMovimiento.CELDA_A_COLUMNA));
            return true;
        }
        return false;
    }

    public boolean moverColumnaAFundacion(int columnaIndex, int fundacionIndex) {
        Columna origen = columnas[columnaIndex];
        Carta carta = origen.verUltimaCarta();
        if (carta != null && fundaciones[fundacionIndex].agregarSiEsValida(carta)) {
            origen.removerUltimaCarta();
            historial.insertaFinal(new Movimiento(origen, fundaciones[fundacionIndex], carta, Movimiento.TipoMovimiento.COLUMNA_A_FUNDACION));
            return true;
        }
        return false;
    }

    public boolean moverCeldaAFundacion(int celdaIndex, int fundacionIndex) {
        Carta carta = celdas.ver(celdaIndex);
        if (carta != null && fundaciones[fundacionIndex].agregarSiEsValida(carta)) {
            celdas.remover(celdaIndex);
            historial.insertaFinal(new Movimiento(celdas, fundaciones[fundacionIndex], carta, Movimiento.TipoMovimiento.CELDA_A_FUNDACION));
            return true;
        }
        return false;
    }

    public boolean moverEntreColumnas(int origenIndex, int destinoIndex) {
        Columna origen = columnas[origenIndex];
        Columna destino = columnas[destinoIndex];
        Carta carta = origen.verUltimaCarta();
        Carta topeDestino = destino.verUltimaCarta();

        if (carta != null && (topeDestino == null || carta.compareTo(topeDestino) < 0)) {
            destino.agregarCarta(carta);
            origen.removerUltimaCarta();
            historial.insertaFinal(new Movimiento(origen, destino, carta, Movimiento.TipoMovimiento.ENTRE_COLUMNAS));
            return true;
        }
        return false;
    }

    public void deshacerUltimoMovimiento() {
        Nodo<Movimiento> ultimo = historial.getInicio();
        if (ultimo == null) return;

        Nodo<Movimiento> anterior = null;
        while (ultimo.getSig() != null) {
            anterior = ultimo;
            ultimo = ultimo.getSig();
        }

        Movimiento movimiento = ultimo.getInfo();
        movimiento.revertir();

        if (anterior == null) {
            historial.eliminaInicio();
        } else {
            anterior.setSig(null);
        }
    }

    public boolean juegoTerminado() {
        for (Fundacion f : fundaciones) {
            if (!f.estaCompleta()) return false;
        }
        return true;
    }

    public Carta darPista() {
        for (int i = 0; i < 8; i++) {
            Carta carta = columnas[i].verUltimaCarta();
            for (Fundacion f : fundaciones) {
                if (carta != null && f.agregarSiEsValida(carta)) {
                    f.getCartas().eliminaFinal(); // revertir prueba
                    return carta;
                }
            }
        }
        return null;
    }

    public Columna[] getColumnas() {
        return columnas;
    }

    public CeldaLibre getCeldas() {
        return celdas;
    }

    public Fundacion[] getFundaciones() {
        return fundaciones;
    }
}
