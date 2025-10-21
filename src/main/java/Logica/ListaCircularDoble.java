package Logica;

public class ListaCircularDoble<T> {
    private NodoDoble<T> inicio;
    private NodoDoble<T> fin;

    public ListaCircularDoble() {
        inicio = null;
        fin = null;
    }

    public void insertaInicio(T dato) {
        NodoDoble<T> nuevo = new NodoDoble<>(dato);
        if (inicio == null) {
            inicio = fin = nuevo;
            nuevo.setSig(nuevo);
            nuevo.setAnt(nuevo);
        } else {
            nuevo.setSig(inicio);
            nuevo.setAnt(fin);
            inicio.setAnt(nuevo);
            fin.setSig(nuevo);
            inicio = nuevo;
        }
    }

    public void insertaFinal(T dato) {
        NodoDoble<T> nuevo = new NodoDoble<>(dato);
        if (inicio == null) {
            inicio = fin = nuevo;
            nuevo.setSig(nuevo);
            nuevo.setAnt(nuevo);
        } else {
            nuevo.setSig(inicio);
            nuevo.setAnt(fin);
            fin.setSig(nuevo);
            inicio.setAnt(nuevo);
            fin = nuevo;
        }
    }

    public void eliminaInicio() {
        if (inicio == null) {
            System.out.println("Lista vacía");
        } else if (inicio == fin) {
            inicio = fin = null;
        } else {
            inicio = inicio.getSig();
            inicio.setAnt(fin);
            fin.setSig(inicio);
        }
    }

    public void eliminaFinal() {
        if (inicio == null) {
            System.out.println("Lista vacía");
        } else if (inicio == fin) {
            inicio = fin = null;
        } else {
            fin = fin.getAnt();
            fin.setSig(inicio);
            inicio.setAnt(fin);
        }
    }

    public void recorrer() {
        if (inicio == null) {
            System.out.println("Lista vacía");
            return;
        }
        NodoDoble<T> actual = inicio;
        do {
            System.out.print(actual.getInfo() + " ");
            actual = actual.getSig();
        } while (actual != inicio);
        System.out.println();
    }

    public NodoDoble<T> getInicio() {
        return inicio;
    }

    public NodoDoble<T> getFin() {
        return fin;
    }
}