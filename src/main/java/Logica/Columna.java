package Logica;

public class Columna {

    private Lista<Carta> cartas;

    public Columna() {
        cartas = new Lista<>();
    }

    public void agregarCarta(Carta carta) {
        cartas.insertaFinal(carta); // se agrega al final para mantener orden visual
    }

    public Carta verUltimaCarta() {
        Nodo<Carta> actual = obtenerUltimoNodo();
        return actual != null ? actual.getInfo() : null;
    }

    public Carta removerUltimaCarta() {
        Carta ultima = verUltimaCarta();
        cartas.eliminaFinal();
        return ultima;
    }

    public boolean estaVacia() {
        return verUltimaCarta() == null;
    }

    public void mostrarColumna() {
        cartas.recorrer();
    }

    private Nodo<Carta> obtenerUltimoNodo() {
        Nodo<Carta> actual = null;
        Nodo<Carta> temp = cartas.getInicio();
        while (temp != null) {
            actual = temp;
            temp = temp.getSig();
        }
        return actual;
    }

    public Lista<Carta> getCartas() {
        return cartas;
    }

    public boolean puedeRecibir(Carta carta) {
        Carta cima = verUltimaCarta();
        if (cima == null) {
            return true;
        }
        return carta.getValor() == cima.getValor() - 1 && carta.getPalo() == cima.getPalo();
    }
}
