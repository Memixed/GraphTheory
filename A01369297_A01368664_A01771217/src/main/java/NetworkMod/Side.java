/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NetworkMod;


//Esta es la estructura de datos para los cables, las aristas

public class Side {
    //Variables para que exista, la longitud, el flujo de datos que puede tener, y los nodos de origen y destino.
    public int len;
    public int flux;
    protected int fluxNow;
    public int actualflux;
    public Node origin;
    public Node end;

    // Las siguientes tres variables son para saber si es necesario para un recorrido segun un algoritmo
    //Si está en true, hay que pintar esta arista de color, si está en false, hay que pintarla en gris/negro.
    public Boolean activeSpan;
    public Boolean activeHamilton;
    public Boolean activeFlux;

    //Este es el constructor que he utilizado, realmente no se usa en ninguna instancia más allá de cuando se crea/carga el grafo
    Side(int l, int f, Node o, Node e){
        this.len = l;
        this.flux = f;
        this.origin = o;
        this.end = e;
        this.activeSpan = false;
        this.activeHamilton = false;
        this.activeFlux = false;
        this.actualflux = 0;
        this.fluxNow = f;
    }

    //Función auxiliar, imprime en un formato bonito la arista.
    public void print_Side() {
        System.out.println(this.origin.id+"->"+this.end.id);
    }
    protected void print_Side_flux() {
        System.out.println(this.origin.id+"->"+this.end.id+" "+this.fluxNow);
    }

    //Función auxiliar, elimina todas las marcas de si la arista es util o no.
    public void reset() {
        this.activeHamilton = false;
        this.activeFlux = false;
        this.activeSpan = false;
        this.fluxNow = this.flux;
        this.actualflux = 0;
    }
}
