
package NetworkMod;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

//Clase auxiliar para implementar una Cola de Prioridad con nuestras estructuras de datos.
class Scomparator implements Comparator<Side>{
    public int compare(Side x, Side y) {
        return x.len-y.len;
    }
}

class Fcomparator implements Comparator<Side>{
    public int compare(Side x, Side y) {
        return x.fluxNow-y.fluxNow;
    }
}

//Clase principal que se exporta
public class Graph {
    // Datos importantes, lista de nodos y aristas.
    public ArrayList <Node> existing_romes = new ArrayList<>();
    public ArrayList <Side> existing_roads = new ArrayList<>();
    public ArrayList <int[]> centrales= new ArrayList<>();

    Cluster map = new Cluster(centrales, existing_romes, new int[]{700,700});

    public void show_clusters() {
        map.show_map();
    }

    public void add_central(int[] coord) {
        this.centrales.add(coord);
    }

    //Agrega nodos al grafo.
    public void add_node(int x, int y, String name) {
        this.existing_romes.add(new Node(x,y,name));
    }

    // Agrega caminos al grafo, verifica primero que existan los nodos que conecta.
    public void add_road(String C1, String C2, int len, int flux) {
        Boolean one = false;
        Boolean two = false;
        Node origin = null,end = null;
        for(Node x : this.existing_romes) {
            if(x.id.equals(C1)) {
                origin = x;
                one = true;
            }
            if(x.id.equals(C2)) {
                end = x;
                two = true;
            }
            if(one && two) {
                this.existing_roads.add(new Side(len,flux,origin,end));
                origin.add_Eroad(this.existing_roads.get(this.existing_roads.size()-1));
                end.add_Iroad(this.existing_roads.get(this.existing_roads.size()-1));
                break;
            }
        }
    }

    // Elimina un camino.
    public void remove_road(String C1,String C2) {
        for(Side x : this.existing_roads) {
            if(x.origin.id.equals(C1) && x.end.id.equals(C2)) {
                x.origin.remove_Eroad(x);
                x.end.remove_Iroad(x);
                this.existing_roads.remove(x);
                break;
            }
        }
    }

    //Elimina un nodo, así como todos los caminos conectados con este.
    public void remove_node(String name) {
        for(Node x : this.existing_romes) {
            if(x.id.equals(name)) {
                for(Side y : x.exit_roads) {
                    this.existing_roads.remove(y);
                }
                for(Side y : x.incoming_roads) {
                    this.existing_roads.remove(y);
                }
                this.existing_romes.remove(x);
                break;
            }
        }
    }

    public void fill_nodes(ArrayList<String> colonias) {
	for(int i=0;i<colonias.size();i=i+3) {
            this.add_node(Integer.parseInt(colonias.get(i+1)),Integer.parseInt(colonias.get(i+2)),colonias.get(i));
	}
    }

    public void fill_roads(ArrayList<String> sides) {
        for(int i=0;i<sides.size();i=i+4) {
            this.add_road(sides.get(i),sides.get(i+1),Integer.parseInt(sides.get(i+2)),Integer.parseInt(sides.get(i+3)));
        }
    }

    public void fill_centrals(ArrayList<int[]> centrales) {
	for(int[] x : centrales) {
            this.add_central(x);
	}
    }


    //Función que resetea todos los caminos, les quita el color, es importante hacerlo cada que se hace un nuevo calculo de algún camino/cadena.
    public void reset_color() {
        for(Side x : this.existing_roads) {
            x.reset();
        }
    }

    //Función que calcula el Spanning Tree a partir de un nodo específico. Apenas haré pruebas de si es necesario especificarlo o lo dará sin importar dónde se inicie. Recuérdenme cambiarlo el martes.
    protected void SpanCalculation() {
        ArrayList <Node> visited = new ArrayList<>();
        PriorityQueue<Side> to_visit = new PriorityQueue<>(this.existing_roads.size(),new Scomparator());
        this.reset_color();
        for(Node x : this.existing_romes) {
            if (x.id.equals(existing_romes.get(0).id)){
                visited.add(x);
                for(Side y : x.exit_roads) {
                    to_visit.add(y);
                }
                break;
            }
        }
        Side playing_side = to_visit.poll();
        while(visited.size() < this.existing_romes.size() && !to_visit.isEmpty()) {
            if(visited.indexOf(playing_side.end) == -1) {
                visited.add(playing_side.end);
                playing_side.activeSpan = true;
                for(Side y : playing_side.end.exit_roads) {
                    to_visit.add(y);
                }
            }
            playing_side = to_visit.poll();
        }
    }

    // A partir de este punto, inicia lo necesario para el cálculo del ciclo de Hamilton, o del agente viajero.
    // Primero un par de sets para guardar lo que llevamos visitado
    private Set<Node> out = new HashSet<Node>();
    private Set<Side> HamiltonPath = new HashSet<Side>();
    // Luego viene nuestro comparador para ver cual es la distancia total que debe recorrer el agente.
    private int HamiltonianLen=100000+7;
    // Y finalmente la función recursiva, porque, si, es recursivo este pedo.
    private void recursiveHamilton(Node x, int len,Node start, Boolean doubled) {
        if(this.HamiltonPath.size() == this.existing_romes.size()) {
            if (len < this.HamiltonianLen) {
                this.HamiltonianLen = len;
                this.reset_color();
                for(Side road : this.HamiltonPath) {
                    road.activeHamilton = true;
                }
            }
        }
        else {
            this.out.add(x);
            for(Side y : x.exit_roads) {
                if(!out.contains(y.end)) {
                    this.HamiltonPath.add(y);
                    this.recursiveHamilton(y.end,len+y.len,start,doubled);
                    this.HamiltonPath.remove(y);
                }
                else {
                    if(y.end == start && this.HamiltonPath.size() == this.existing_romes.size()-1 && !doubled) {
                        this.HamiltonPath.add(y);
                        this.recursiveHamilton(y.end,len+y.len,start,true);
                        this.HamiltonPath.remove(y);
                    }
                }
            }
            this.out.remove(x);
        }
    }

    //Función del usuario, para calcular el camino hamiltoneano. Resulta que le da igual dónde inicie, siempre dará el mismo.
    protected void HamiltonianCircuit() {
        this.reset_color();
        this.recursiveHamilton(this.existing_romes.get(1),0,this.existing_romes.get(1),false);
    }

    //De este punto en adelante, se implementa el algoritmo para maximo flujo.
    private Set<Side> BFSQuery = new HashSet<Side>();
    private Set<Node> BFSvisited = new HashSet<Node>();
    Comparator<Side> fluxComparator = (c1, c2) -> c2.fluxNow - c1.fluxNow;
    private int BFS(Node starter, Node ender, int minimal) {
        if(starter == ender) {
            return (minimal);
        }
        else {
            int expectedFlux;
            starter.exit_roads.sort(fluxComparator);
            for(Side x : starter.exit_roads) {
                if((x.fluxNow > 0) && (BFSvisited.add(x.end))){
                    BFSQuery.add(x);
                    if(x.fluxNow < minimal) {
                        minimal = x.fluxNow;
                    }
                    expectedFlux = BFS(x.end,ender,minimal);
                    if(expectedFlux != -1) {
                        return (expectedFlux);
                    }
                    else {
                        BFSQuery.remove(x);
                        BFSvisited.remove(x.end);
                        continue;
                    }
                }
            }

            return -1;
        }
    }
    protected int fordFurkerson(String start, String end) {
        Node s = null, f = null;
        int MaxFlux = 0;
        for(Node x : this.existing_romes) {
            if(x.id.equals(start)) {
                s = x;
            }
            if(x.id.equals(end)) {
                f = x;
            }
        }
        this.reset_color();
        this.BFSQuery.clear();
        this.BFSvisited.clear();
        int potentialflux = 100007;
        this.BFSvisited.add(s);
        int flux = this.BFS(s,f,potentialflux);
        while(flux != -1) {
            for(Side x : this.BFSQuery) {
                x.activeFlux = true;
                x.fluxNow = x.fluxNow - flux;
                x.actualflux +=flux;
            }
            MaxFlux +=flux;
            this.BFSQuery.clear();
            this.BFSvisited.clear();
            this.BFSvisited.add(s);
            flux = this.BFS(s,f,potentialflux);

        }
        return (MaxFlux);
    }

    // EndPoints
    public HashMap<String,Boolean> get_Hamilton(){
        HashMap<String,Boolean> aux = new HashMap<>();
        this.HamiltonianCircuit();
        for(Side x : this.existing_roads) {
            aux.put(x.origin.id+"-"+x.end.id, x.activeHamilton);
        }
        return aux;
    }
    public HashMap<String,Boolean> get_Span(){
        HashMap<String,Boolean> aux = new HashMap<>();
        this.SpanCalculation();
        for(Side x : this.existing_roads) {
            aux.put(x.origin.id+"-"+x.end.id, x.activeSpan);
        }
        return aux;
    }
    public HashMap<String,int[]> get_Flux_detailed(String c1, String c2){
        HashMap<String,int[]> aux = new HashMap<>();
        this.fordFurkerson(c1,c2);
        for(Side x : this.existing_roads) {
            aux.put(x.origin.id+"-"+x.end.id, new int[]{(x.activeFlux ? 1 : 0),x.flux,x.actualflux});
        }
        return aux; //String[Key], int[0:not active 1:active, total flux, used flux]
    }
    public HashMap<String, Boolean> get_Flux(){
        HashMap<String,Boolean> aux = new HashMap<>();
        this.HamiltonianCircuit();
        for(Side x : this.existing_roads) {
            aux.put(x.origin.id+"-"+x.end.id, x.activeFlux);
        }
        return aux; //String[Key], int[0:not active 1:active, total flux, used flux]
    }
    
    public int get_max_Flux_Value(String c1, String c3){
        int max_flux = this.fordFurkerson(c1,c3);
        return max_flux;
    }
    public ArrayList<String> get_deleted_Roads(){
        ArrayList<String> aux = new ArrayList<>();
        for(Side x : this.existing_roads){
            aux.add(x.origin.id);
            aux.add(x.end.id);
            aux.add(Integer.toString(x.len));
            aux.add(Integer.toString(x.flux));
        }
        return aux;
    }

    public ArrayList<String> get_nodes_strings(){
        ArrayList<String> aux = new ArrayList<>();
        for(Node x : this.existing_romes){
            aux.add(x.id);
            aux.add(Integer.toString(x.coordx));
            aux.add(Integer.toString(x.coordy));
        }
        return aux;
    }

    public HashMap <String, long[]> get_deleted_nodes(){
        HashMap <String, long[]> aux = new HashMap<>();
        for(Node x : this.existing_romes){
            aux.put(x.id, new long[]{x.coordx, x.coordy});
        }
        return aux;

    }

}
