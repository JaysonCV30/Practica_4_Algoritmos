package Logica;

public class NodoDoble <T>{
    private T info;
    private NodoDoble<T> sig;
    private NodoDoble<T> ant;

    public NodoDoble(T nuevaInfo, NodoDoble<T> nuevoSig, NodoDoble<T> nuevoAnt) {
        this.info = nuevaInfo;
        this.sig = nuevoSig;
        this.ant = nuevoAnt;
    }

    public NodoDoble(T nuevaInfo) {
        this(nuevaInfo, null, null);
    }

    public T getInfo() {
        return info;
    }

    public NodoDoble<T> getSig() {
        return sig;
    }

    public NodoDoble<T> getAnt() {
        return ant;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public void setSig(NodoDoble<T> sig) {
        this.sig = sig;
    }

    public void setAnt(NodoDoble<T> ant) {
        this.ant = ant;
    }
}
