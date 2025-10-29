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

    public boolean moverCartaDesdeColumna(int destino, Carta carta) {
        int origen = -1;
        for (int i = 0; i < columnas.length; i++) {
            if (columnas[i].verUltimaCarta() == carta) {
                origen = i;
                break;
            }
        }
        if (origen == -1) {
            return false;
        }

        // Destino: columna
        if (destino >= 0 && destino < 8) {
            if (columnas[destino].puedeRecibir(carta)) {
                columnas[origen].removerUltimaCarta();
                columnas[destino].agregarCarta(carta);
                registrarMovimiento(origen, destino, carta);
                return true;
            }
        }

        // Destino: celda libre
        if (destino >= 8 && destino < 16) {
            int celda = destino - 8;
            if (celdas.ver(celda) == null) {
                columnas[origen].removerUltimaCarta();
                celdas.agregar(carta); // agrega en la primera disponible
                registrarMovimiento(origen, destino, carta);
                return true;
            }
        }

        // Destino: fundación
        if (destino >= 16 && destino < 20) {
            int fund = destino - 16;
            if (fundaciones[fund].puedeRecibir(carta)) {
                columnas[origen].removerUltimaCarta();
                fundaciones[fund].agregarCarta(carta);
                registrarMovimiento(origen, destino, carta);
                return true;
            }
        }

        return false;
    }

    public boolean moverCartaDesdeCelda(int destino, Carta carta) {
        int origen = -1;
        for (int i = 0; i < 8; i++) {
            if (celdas.ver(i) == carta) {
                origen = i;
                break;
            }
        }
        if (origen == -1) {
            return false;
        }

        // Destino: columna
        if (destino >= 0 && destino < 8) {
            if (columnas[destino].puedeRecibir(carta)) {
                celdas.remover(origen);
                columnas[destino].agregarCarta(carta);
                registrarMovimiento(origen + 8, destino, carta);
                return true;
            }
        }

        // Destino: fundación
        if (destino >= 16 && destino < 20) {
            int fund = destino - 16;
            if (fundaciones[fund].puedeRecibir(carta)) {
                celdas.remover(origen);
                fundaciones[fund].agregarCarta(carta);
                registrarMovimiento(origen + 8, destino, carta);
                return true;
            }
        }

        return false;
    }

    public boolean moverCartaDesdeFundacion(int destino, Carta carta) {
        int origen = -1;
        for (int i = 0; i < fundaciones.length; i++) {
            if (fundaciones[i].verTope() == carta) {
                origen = i;
                break;
            }
        }
        if (origen == -1) {
            return false;
        }

        // Destino: columna
        if (destino >= 0 && destino < 8) {
            if (columnas[destino].puedeRecibir(carta)) {
                fundaciones[origen].retirarCima();
                columnas[destino].agregarCarta(carta);
                registrarMovimiento(origen + 16, destino, carta);
                return true;
            }
        }

        // Destino: celda libre
        if (destino >= 8 && destino < 16) {
            int celda = destino - 8;
            if (celdas.ver(celda) == null) {
                fundaciones[origen].retirarCima();
                celdas.agregar(carta);
                registrarMovimiento(origen + 16, destino, carta);
                return true;
            }
        }

        return false;
    }

    private void registrarMovimiento(int origenIndex, int destinoIndex, Carta carta) {
        Object origen;
        Object destino;
        Movimiento.TipoMovimiento tipo;

        // Determinar origen
        if (origenIndex >= 0 && origenIndex < 8) {
            origen = columnas[origenIndex];
        } else if (origenIndex >= 8 && origenIndex < 16) {
            origen = celdas;
        } else {
            origen = fundaciones[origenIndex - 16];
        }

        // Determinar destino
        if (destinoIndex >= 0 && destinoIndex < 8) {
            destino = columnas[destinoIndex];
        } else if (destinoIndex >= 8 && destinoIndex < 16) {
            destino = celdas;
        } else {
            destino = fundaciones[destinoIndex - 16];
        }

        // Determinar tipo de movimiento
        if (origen instanceof Columna && destino instanceof CeldaLibre) {
            tipo = Movimiento.TipoMovimiento.COLUMNA_A_CELDA;
        } else if (origen instanceof CeldaLibre && destino instanceof Columna) {
            tipo = Movimiento.TipoMovimiento.CELDA_A_COLUMNA;
        } else if (origen instanceof Columna && destino instanceof Fundacion) {
            tipo = Movimiento.TipoMovimiento.COLUMNA_A_FUNDACION;
        } else if (origen instanceof CeldaLibre && destino instanceof Fundacion) {
            tipo = Movimiento.TipoMovimiento.CELDA_A_FUNDACION;
        } else {
            tipo = Movimiento.TipoMovimiento.ENTRE_COLUMNAS;
        }

        historial.insertaFinal(new Movimiento(origen, destino, carta, tipo));
    }

    public void deshacerUltimoMovimiento() {
        Nodo<Movimiento> ultimo = historial.getInicio();
        if (ultimo == null) {
            return;
        }

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
            if (!f.estaCompleta()) {
                return false;
            }
        }
        return true;
    }

    public Carta darPista() {
        // 1. Buscar As para iniciar fundación
        for (int i = 0; i < 8; i++) {
            Carta carta = columnas[i].verUltimaCarta();
            if (carta != null && carta.getValor() == 1) {
                return carta;
            }
        }

        // 2. Buscar carta que pueda ir a fundación
        for (int i = 0; i < 8; i++) {
            Carta carta = columnas[i].verUltimaCarta();
            for (Fundacion f : fundaciones) {
                if (carta != null && f.puedeRecibir(carta)) {
                    return carta;
                }
            }
        }

        // 3. Buscar carta que pueda moverse entre columnas
        for (int i = 0; i < 8; i++) {
            Carta carta = columnas[i].verUltimaCarta();
            for (int j = 0; j < 8; j++) {
                if (i != j && carta != null && columnas[j].puedeRecibir(carta)) {
                    return carta;
                }
            }
        }

        // 4. Buscar carta que pueda ir a celda libre
        for (int i = 0; i < 8; i++) {
            Carta carta = columnas[i].verUltimaCarta();
            if (carta != null && celdas.hayEspacio()) {
                return carta;
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
