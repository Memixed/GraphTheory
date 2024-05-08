/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NetworkMod;

import java.util.ArrayList;

// Esta es la estructura de datos para los Nodos.

public class Node {
    //Variables para crearlos, coordenadas e identificador.
    public int coordx;
    public int coordy;
    public String id;

    // Variables de apoyo, nodos adjacentes tanto entrantes como salientes. Ayuda a reducir las lineas de codigo de la clase Grafo.
    protected ArrayList<Side> exit_roads = new ArrayList<>();
    protected ArrayList<Side> incoming_roads = new ArrayList<>();

    //Este es el constructor, igual que los lados, sólo es usado cuando se crea/carga/modifica el grafo.
    Node(int x, int y, String name){
        this.coordx = x;
        this.coordy = y;
        this.id = name;
    }
    // Funciones que agregan Caminos de entrada/salida.
    public void add_Eroad(Side s) {
        this.exit_roads.add(s);
    }
    public void add_Iroad(Side s) {
        this.incoming_roads.add(s);
    }
    //Funciones para eliminar caminos de entrada/salida. Solo usado cuando se eliminan caminos.
    protected void remove_Eroad(Side s) {
        this.exit_roads.remove(s);
    }
    protected void remove_Iroad(Side s) {
        this.incoming_roads.remove(s);
    }
}
