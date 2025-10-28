package Logica;


public class CeldaLibre {
    private Lista<Carta> celdas;
    private final int LIMITE = 8;

    public CeldaLibre() {
        celdas = new Lista<>();
    }

    public boolean agregar(Carta carta) {
        if (contarCartas() < LIMITE) {
            celdas.insertaFinal(carta);
            return true;
        } else {
            System.out.println("Ya hay 8 cartas en las celdas libres.");
            return false;
        }
    }

    public Carta ver(int posicion) {
        Nodo<Carta> actual = celdas.getInicio();
        int index = 0;
        while (actual != null) {
            if (index == posicion) {
                return actual.getInfo();
            }
            actual = actual.getSig();
            index++;
        }
        return null;
    }

    public Carta remover(int posicion) {
        if (posicion < 0 || posicion >= contarCartas()) {
            return null;
        }

        Nodo<Carta> actual = celdas.getInicio();
        Nodo<Carta> anterior = null;
        int index = 0;

        while (actual != null) {
            if (index == posicion) {
                Carta carta = actual.getInfo();
                if (anterior == null) {
                    celdas.eliminaInicio();
                } else {
                    anterior.setSig(actual.getSig());
                    if (actual.getSig() == null) {
                        // era el último
                        celdas.eliminaFinal();
                    }
                }
                return carta;
            }
            anterior = actual;
            actual = actual.getSig();
            index++;
        }
        return null;
    }

    public int contarCartas() {
        Nodo<Carta> actual = celdas.getInicio();
        int contador = 0;
        while (actual != null) {
            contador++;
            actual = actual.getSig();
        }
        return contador;
    }

    public void mostrarCeldas() {
        System.out.print("Celdas libres: ");
        celdas.recorrer();
    }

    public Lista<Carta> getCeldas() {
        return celdas;
    }
}
