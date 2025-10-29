package Logica;

public class Fundacion {

    private Lista<Carta> cartas;

    public Fundacion() {
        cartas = new Lista<>();
    }

    public boolean agregarSiEsValida(Carta carta) {
        Carta tope = verTope();
        if (tope == null) {
            if (carta.getValor() == 1) { // Solo se puede iniciar con As
                cartas.insertaFinal(carta);
                return true;
            }
        } else {
            if (carta.esSiguienteDe(tope)) {
                cartas.insertaFinal(carta);
                return true;
            }
        }
        return false;
    }

    public Carta verTope() {
        Nodo<Carta> actual = cartas.getInicio();
        if (actual == null) {
            return null;
        }

        while (actual.getSig() != null) {
            actual = actual.getSig();
        }
        return actual.getInfo();
    }

    public boolean estaCompleta() {
        Carta tope = verTope();
        return tope != null && tope.getValor() == 13;
    }

    public void mostrarFundacion() {
        System.out.print("Fundación: ");
        cartas.recorrer();
    }

    public Lista<Carta> getCartas() {
        return cartas;
    }

    public boolean puedeRecibir(Carta carta) {
        Carta tope = verTope();
        if (tope == null) {
            return carta.getValor() == 1;
        }
        return carta.esSiguienteDe(tope);
    }

    public void agregarCarta(Carta carta) {
        cartas.insertaFinal(carta);
    }

    public Carta retirarCima() {
        Carta cima = verTope();
        if (cima != null) {
            cartas.eliminaFinal();
        }
        return cima;
    }
}
