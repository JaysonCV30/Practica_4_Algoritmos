package Logica;

public class Lista<T> {
    private Nodo<T> inicio;
    private Nodo<T> fin;

    public Lista() {
        inicio = null;
        fin = null;
    }

    public void insertaInicio(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato, inicio);
        inicio = nuevo;
        if (fin == null) {
            fin = nuevo;
        }
    }

    public void insertaFinal(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato, null);
        if (inicio == null) {
            inicio = fin = nuevo;
        } else {
            fin.setSig(nuevo);
            fin = nuevo;
        }
    }

    public void eliminaInicio() {
        if (inicio == null) {
            System.out.println("Lista vacía");
        } else {
            inicio = inicio.getSig();
            if (inicio == null) {
                fin = null;
            }
        }
    }

    public void eliminaFinal() {
        if (inicio == null) {
            System.out.println("Lista vacía");
        } else if (inicio == fin) {
            inicio = fin = null;
        } else {
            Nodo<T> actual = inicio;
            while (actual.getSig() != fin) {
                actual = actual.getSig();
            }
            actual.setSig(null);
            fin = actual;
        }
    }

    public void recorrer() {
        Nodo<T> actual = inicio;
        while (actual != null) {
            System.out.print(actual.getInfo() + " ");
            actual = actual.getSig();
        }
        System.out.println();
    }

    public int buscar(T dato) {
        Nodo<T> actual = inicio;
        int pos = 0;
        while (actual != null) {
            if (actual.getInfo().equals(dato)) {
                return pos;
            }
            actual = actual.getSig();
            pos++;
        }
        return -1;
    }
}