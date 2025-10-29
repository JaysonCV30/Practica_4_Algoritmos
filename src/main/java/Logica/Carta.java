package Logica;

public class Carta implements Comparable<Carta> {
    public enum Palo {
        TREBOL, DIAMANTE, CORAZON, PICA
    }

    private int valor; 
    private Palo palo;
    private boolean visible;

    public Carta(int valor, Palo palo) {
        this.valor = valor;
        this.palo = palo;
        this.visible = true; 
    }

    public int getValor() {
        return valor;
    }

    public Palo getPalo() {
        return palo;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getNombre() {
        return switch (valor) {
            case 1 -> "As";
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";
            default -> String.valueOf(valor);
        };
    }

    public String getSimboloPalo() {
        return switch (palo) {
            case TREBOL -> "trebol";
            case DIAMANTE -> "diamante";
            case CORAZON -> "corazon";
            case PICA -> "pica";
        };
    }

    @Override
    public int compareTo(Carta otra) {
        // Primero compara por palo, luego por valor
        if (this.palo.ordinal() != otra.palo.ordinal()) {
            return this.palo.ordinal() - otra.palo.ordinal();
        }
        return this.valor - otra.valor;
    }

    public boolean esSiguienteDe(Carta otra) {
        // Verifica si esta carta puede colocarse sobre otra en fundación
        return this.palo == otra.palo && this.valor == otra.valor + 1;
    }

    @Override
    public String toString() {
        return getNombre() + getSimboloPalo();
    }
}