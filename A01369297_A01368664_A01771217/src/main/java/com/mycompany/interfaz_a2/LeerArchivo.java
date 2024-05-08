
package com.mycompany.interfaz_a2;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LeerArchivo {
    
    private JSONObject urbe;
    
    public JSONObject Lectura(File arch) throws ParseException{
        JSONParser lectura = new JSONParser();
        /*
        Gson gson = new Gson();
        
        try (FileReader reader = new FileReader(arch)) {
            // Parse JSON file into a Java object
            Grafo data = gson.fromJson(reader, Grafo.class);
            // Use the data object in other classes
            MyClass myClass = new MyClass(data);
            myClass.doSomethingWithData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        
        try(FileReader lector = new FileReader(arch)){
            Object elem = lectura.parse(lector);

            this.urbe = (JSONObject) elem;
            
            return this.urbe;
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, e);
        }
        return this.urbe;
    }
    
}
