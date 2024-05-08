
package com.mycompany.interfaz_a2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Grafo {
    
    private JSONObject urbe;
    private JSONObject cityObject;
    private ArrayList<int[]> centrales;
    private HashMap<String, long[]> colonias;
    private ArrayList<String> enlaces;
    private ArrayList<String> coloniaS;
    
    public void setGrafo(File a) throws ParseException{

        LeerArchivo grafo = new LeerArchivo();
        
        try {
            this.urbe = grafo.Lectura(a);
            this.cityObject = (JSONObject) urbe.get("ciudad");
            
            this.setCentrales();
            this.setColonias();
            this.setEnlaces();
                        
        } catch (ParseException ex) {
            System.out.println("No se pudo leer el archivo");
        }        
    }
    
    public void setCentrales(){
        //centrales
        JSONArray centra = (JSONArray) (this.cityObject).get("centrales");
        this.centrales = new ArrayList<>();

        for(Object punto: centra){
            JSONObject coord = (JSONObject) punto;
            int aux[] = new int[2];

            String xs = (String) coord.get("x");
            aux[0] = Integer.parseInt(xs);

            String ys = (String) coord.get("y");
            aux[1] = Integer.parseInt(ys);

            this.centrales.add(aux);              
        } 
    }
    
    private void setColonias(){
        //Colonias
        JSONArray col = (JSONArray) this.cityObject.get("colonias");
        this.colonias = new HashMap<String, long[]>();
        this.coloniaS = new ArrayList<String>();

        for(Object punto: col){
            JSONObject coord = (JSONObject) punto;
            long aux[] = new long[2];
            
            String nom = (String) coord.get("nombre");
            this.coloniaS.add(nom);

            String xs = (String) coord.get("coordenadaX");
            this.coloniaS.add(xs);
            aux[0] = Integer.parseInt(xs);

            long ys = (long) coord.get("coordenadaY");
            this.coloniaS.add(Long.toString(ys));
            aux[1] = ys;

            this.colonias.put(nom, aux);

        }  
    }
    
    public void setEnlaces(){
        JSONArray en = (JSONArray) this.cityObject.get("enlaces");
        this.enlaces = new ArrayList<String>();
        
        for(Object punto: en){
          
            JSONObject coord = (JSONObject) punto;
            
            String ini = (String) coord.get("coloniaInicial");
            this.enlaces.add(ini);
            
            String fin = (String) coord.get("coloniaFinal");
            this.enlaces.add(fin);
            
            long dist = (long) coord.get("distancia");
            String distancia = Long.toString(dist);
            this.enlaces.add(distancia);
            
            long cap = (long) coord.get("capacidad");
            String capacidad = Long.toString(cap);
            this.enlaces.add(capacidad);
        }  
    }
    
    public ArrayList<int[]> getCentrales(){  
        return this.centrales;
    }
    
    public ArrayList<String> getEnlaces(){
        return this.enlaces;     
    }
    
    public HashMap<String, long[]> getColonias(){
        return this.colonias;
    }
    
    public ArrayList<String> getColoniasStr() {
        return this.coloniaS;
    }
    
    public ArrayList<String> getStrCol(){
        return this.coloniaS;
    }
    
    public void nuevaCol(String nombre, int x, int y){
        this.coloniaS.add(nombre);
        this.coloniaS.add(Integer.toString(x));
        this.coloniaS.add(Integer.toString(y));
        
        long aux[] = new long[2];
        aux[0]= x;
        aux[1] = y;
        this.colonias.put(nombre,aux);
    }
    
    public void eliminaColonia(String nombreColonia) {
        this.colonias.remove(nombreColonia);
        //this.eliminarEnlacesDeColonia(nombreColonia);
        for (int i = 0; i <this.enlaces.size(); i += 3) {
            String coloniaInicial = this.coloniaS.get(i);

            if (coloniaInicial.equals(nombreColonia)) {
                this.enlaces.remove(i);
                this.enlaces.remove(i+1);
                this.enlaces.remove(i+2);
                break;
            }
        }
    }

    public void eliminarEnlace(String nombreColoniaI, String nombreColoniaF) {
        // Iteramos sobre la lista de enlaces y eliminamos los enlaces que involucran a la colonia
        for (int i = 0; i <this.enlaces.size(); i += 4) {
            String coloniaInicial = this.enlaces.get(i);
            String coloniaFinal = this.enlaces.get(i + 1);

            if (coloniaInicial.equals(nombreColoniaI) && coloniaFinal.equals(nombreColoniaF)) {
                this.enlaces.remove(i);
                this.enlaces.remove(i+1);
                this.enlaces.remove(i+2);
                this.enlaces.remove(i+3);
                break;
            }
        }
    }
    
    public void eliminarEnlacesDeColonia(String nombreColonia) {
        // Iteramos sobre la lista de enlaces y eliminamos los enlaces que involucran a la colonia
        boolean exist = true;
        while (exist){
            exist = false;
            for (int i = 0; i <this.enlaces.size(); i =i+ 4) {
                String coloniaInicial = this.enlaces.get(i);
                String coloniaFinal = this.enlaces.get(i + 1);

                if (coloniaInicial.equals(nombreColonia) || coloniaFinal.equals(nombreColonia)) {
                    exist = true;
                    this.enlaces.remove(i);
                    this.enlaces.remove(i+1);
                    this.enlaces.remove(i+2);
                    this.enlaces.remove(i+3);
                    break;
                }
            }
        }
    }

    public void agregarColonia(String nombre, String x, String y) {
        if (this.colonias.containsKey(nombre)) {
            System.out.println("La colonia " + nombre + " ya existe.");
        } else {
            this.coloniaS.add(nombre);

            long[] coordenadas = new long[2];
            coordenadas[0] = Long.parseLong(x);
            coordenadas[1] = Long.parseLong(y);

            this.colonias.put(nombre, coordenadas);

            System.out.println("Colonia fue agregada exitosamente  : " + nombre);
            System.out.println("Coordenadas de la nueva colonia son: (" + x + ", " + y + ")");
        }
    }
    
    @SuppressWarnings("unchecked")
    public void save_current_graph(){
        JSONObject auxiliar = new JSONObject();
        JSONArray col = new JSONArray();
        JSONArray road = new JSONArray();
        JSONArray central = new JSONArray();

        for (int i =0; i<this.coloniaS.size(); i=i+3){
            JSONObject colObj = new JSONObject();
            colObj.put("coordenadaY", Long.valueOf(this.coloniaS.get(i+2)));
            colObj.put("coordenadaX", this.coloniaS.get(i+1));
            colObj.put("nombre", this.coloniaS.get(i));
            col.add(colObj);
        }
        for (int i =0; i<this.enlaces.size(); i=i+4){
            JSONObject roadObj = new JSONObject();
            roadObj.put("capacidad", Long.valueOf(this.enlaces.get(i+3)));
            roadObj.put("distancia", Long.valueOf(this.enlaces.get(i+2)));
            roadObj.put("coloniaFinal", this.enlaces.get(i+1));
            roadObj.put("coloniaInicial", this.enlaces.get(i));
            road.add(roadObj);
        }
        for(int[] c : this.centrales){
            JSONObject centralObj = new JSONObject();
            centralObj.put("x", c[0]);
            centralObj.put("y", c[1]);
            central.add(centralObj);
        }
        auxiliar.put("centrales",central);
        auxiliar.put("enlaces",road);
        auxiliar.put("colonias",col);
        System.out.println(auxiliar.toJSONString());
        File filer=new File("C:\\Users\\josem\\Downloads\\GrafoActualizado.json");  
        try {
            filer.createNewFile();
        } catch (IOException e) {
            System.out.println("Error al crear el archivo");
            e.printStackTrace();
        } 
        try (FileWriter file = new FileWriter(filer)) {
            //We can write any JSONArray or JSONObject instance to the file
            System.out.println(auxiliar.toString());
            file.write(auxiliar.toJSONString()); 
            file.flush();
            file.close();
            System.out.println(Path.of("GrafoActualizado.json").toAbsolutePath());
 
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo");
            e.printStackTrace();
        }
    }
    
    public void agregarEnlace(String coloniaInicial, String coloniaFinal, String distancia, String capacidad) {
    // Creamos un nuevo enlace y lo agregamos a la lista de enlaces
        this.enlaces.add(coloniaInicial);
        this.enlaces.add(coloniaFinal);
        this.enlaces.add(distancia);
        this.enlaces.add(capacidad);
    }
    public void fill_other_date(HashMap<String, long[]> colonias, ArrayList<String> coloniaS, ArrayList<String> enlaces){
        this.colonias = colonias;
        this.coloniaS = coloniaS;
        this.enlaces = enlaces;
        
    }
    
}
