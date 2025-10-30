package Logica;

import java.util.*;

public class Baraja {
    private ListaCircularDoble<Carta> cartas;
    
    public Baraja(){
        cartas = new ListaCircularDoble<>();
        generarBaraja();
    }
    
    private void generarBaraja(){
        for(Carta.Palo palo : Carta.Palo.values()){
            for(int i = 1; i <= 13; i++){
                Carta carta = new Carta(i, palo);
                cartas.insertaFinal(carta);
            }
        }
    }
    
    public void mezclar(){
        Carta[] arreglo = new Carta[52];
        NodoDoble<Carta> actual = cartas.getInicio();
        
        for(int i = 0; i < 52; i++){
            arreglo[i] = actual.getInfo();
            actual = actual.getSig();
        }
        
        // Mezclar el arreglo temporal
        Random random = new Random();
        for(int i = 0; i < arreglo.length; i++){
            int j = random.nextInt(arreglo.length);
            Carta temp = arreglo[i];
            arreglo[i] = arreglo[j];
            arreglo[j] = temp;
        }
        
        // Se vacia la lista para volver a insertar las cartas pero ya mezcladas
        cartas = new ListaCircularDoble<>();
        for(Carta carta : arreglo){
            cartas.insertaFinal(carta);
        }
    }
    
    public Carta sacarCarta(){
        if(cartas.getInicio() == null){
            return null;
        }
        
        Carta carta = cartas.getInicio().getInfo();
        cartas.eliminaInicio();
        return carta;
    }
    
    public boolean estaVacia(){
        return cartas.getInicio() == null;
    }
    
    public void mostrarBaraja(){
        cartas.recorrer();
    }
}
