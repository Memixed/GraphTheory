
package gui;

import com.mycompany.interfaz_a2.Dibuja;
import com.mycompany.interfaz_a2.Grafo;
import NetworkMod.*;
//import com.mycompany.interfaz_a2.LeerArchivo;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class Pantalla extends javax.swing.JFrame {
    
    private final Grafo g = new Grafo();
    private ArrayList<int[]> centra;
    private ArrayList<String> en;
    private ArrayList<String> colS;
    private HashMap<String, long[]> col;
    private final Dibuja d = new Dibuja();
    private final Graph algo = new Graph();

    public Pantalla() {
        initComponents();
    }

    private void initComponents() {

        ciudad = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        cargar = new javax.swing.JMenu();
        guardar = new javax.swing.JMenu();
        cambiar = new javax.swing.JMenu();
        agregar = new javax.swing.JMenu();
        agColonia = new javax.swing.JMenuItem();
        agEnlace = new javax.swing.JMenuItem();
        borrar = new javax.swing.JMenu();
        elColonia = new javax.swing.JMenuItem();
        elEnlace = new javax.swing.JMenuItem();
        mostrar = new javax.swing.JMenu();
        cableado = new javax.swing.JMenuItem();
        ruta = new javax.swing.JMenuItem();
        flujo = new javax.swing.JMenuItem();
        centrales = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout ciudadLayout = new javax.swing.GroupLayout(ciudad);
        ciudad.setLayout(ciudadLayout);
        ciudadLayout.setHorizontalGroup(
            ciudadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 807, Short.MAX_VALUE)
        );
        ciudadLayout.setVerticalGroup(
            ciudadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 519, Short.MAX_VALUE)
        );

        cargar.setText("Cargar Grafo");
        cargar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cargarMouseClicked(evt);
            }
        });
        jMenuBar1.add(cargar);

        guardar.setText("Guardar Grafo");
        guardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                guardarActionPerformed(evt);
            }
        });
        jMenuBar1.add(guardar);

        cambiar.setText("Modificar");

        agregar.setText("Agregar");

        agColonia.setText("Colonia");
        agColonia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agColoniaMouseClicked(evt);
            }
        });
        agregar.add(agColonia);

        agEnlace.setText("Enlace");
        agEnlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agEnlaceMouseClicked(evt);
            }
        });
        agregar.add(agEnlace);

        cambiar.add(agregar);

        borrar.setText("Eliminar");

        elColonia.setText("Colonia");
        elColonia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elColoniaMouseClicked(evt);
            }
        });
        borrar.add(elColonia);

        elEnlace.setText("Enlace");
        elEnlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elEnlaceMouseClicked(evt);
            }
        });
        borrar.add(elEnlace);

        cambiar.add(borrar);

        jMenuBar1.add(cambiar);

        mostrar.setText("Mostrar");

        cableado.setText("Cableado Óptimo");
        cableado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rutaSpanningTree(evt);
            }
        });
        mostrar.add(cableado);

        ruta.setText("Ruta más corta");
        ruta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rutaActionPerformed(evt);
            }
        });
        mostrar.add(ruta);

        flujo.setText("Flujo de Información");
        flujo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flujoActionPerformed(evt);
            }
        });
        mostrar.add(flujo);

        centrales.setText("Centrales");
        centrales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centralesActionPerformed(evt);
            }
        });
        mostrar.add(centrales);

        jMenuBar1.add(mostrar);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ciudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ciudad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cargarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cargarMouseClicked
        
        JFileChooser jsonArch = new JFileChooser();
        
        int v = jsonArch.showOpenDialog(this);
        
        if (v == JFileChooser.APPROVE_OPTION) {
            File archSelected = jsonArch.getSelectedFile();
            
            //Grafo g = new Grafo();           
            //Dibuja d = new Dibuja();
            
            try {
                
                this.g.setGrafo(archSelected);
                
                this.centra = this.g.getCentrales();               
                this.en = this.g.getEnlaces();                                
                this.col = this.g.getColonias();
                this.colS = this.g.getColoniasStr();
                
                this.algo.fill_nodes(this.colS);
                this.algo.fill_roads(this.en);
                this.algo.fill_centrals(this.centra);
                  
                this.d.dibujaCentrales(this.ciudad.getGraphics(), ciudad.getHeight(), this.centra);
                this.d.dibujaEnlaces(this.ciudad.getGraphics(),this.col,this.en, ciudad.getHeight());
                this.d.dibujaColonias(this.ciudad.getGraphics(), this.col, ciudad.getHeight());
                
            } catch (ParseException ex) {
                
                Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_cargarMouseClicked

    private void agColoniaMouseClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agColoniaMouseClicked
        String name = JOptionPane.showInputDialog("Ingresa el nombre de la colonia a agregar, o deja en blanco para cancelar");
        while(true){
            if (!this.col.containsKey(name) || name.equals("")){
                break;
            }
            else{
                name = JOptionPane.showInputDialog("La colonia ya existe, ingresa otro nombre o deja en blanco para cancelar");
            }
        }
        if (!name.equals("")){
            int x = Integer.parseInt(JOptionPane.showInputDialog("Ingresa la coordenada X"));
            while (x < 0 || x > ciudad.getWidth()){
                x = Integer.parseInt(JOptionPane.showInputDialog("Ingresa una coordenada X valida"));
            }
            int y = Integer.parseInt(JOptionPane.showInputDialog("Ingresa la coordenada Y"));
            while (y < 0 || y > ciudad.getHeight()){
                y = Integer.parseInt(JOptionPane.showInputDialog("Ingresa una coordenada Y valida"));
            }
            this.g.nuevaCol(name, x, y);
            this.algo.add_node(x, y, name);
            this.col = this.algo.get_deleted_nodes();
            this.en = this.algo.get_deleted_Roads();
            this.d.dibujaColonias(this.ciudad.getGraphics(), col, ciudad.getHeight());
            this.d.dibujaCentrales(this.ciudad.getGraphics(), ciudad.getHeight(), this.centra);
            this.d.dibujaEnlaces(this.ciudad.getGraphics(),this.col,this.en, ciudad.getHeight());
        }
    }//GEN-LAST:event_agColoniaMouseClicked

    private void elColoniaMouseClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agColoniaMouseClicked
        String name = JOptionPane.showInputDialog("Ingresa el nombre de la colonia a eliminar, o deja en blanco para cancelar");
        while(true){
            if (this.col.containsKey(name) || name.equals("")){
                break;
            }
            else{
                name = JOptionPane.showInputDialog("La colonia no existe, ingresa otro nombre o deja en blanco para cancelar");
            }
        }
        if (!name.equals("")){
            this.g.eliminaColonia(name);
            this.algo.remove_node(name);
            this.col = this.algo.get_deleted_nodes();
            this.en = this.algo.get_deleted_Roads();
            this.d.updateGrafoGeneral(ciudad.getGraphics(), ciudad.getHeight(), this.centra, this.col, this.en, ciudad.getWidth());                
            this.d.dibujaCentrales(this.ciudad.getGraphics(), ciudad.getHeight(), this.centra);
            this.d.dibujaEnlaces(this.ciudad.getGraphics(),this.col,this.en, ciudad.getHeight());
            this.d.dibujaColonias(this.ciudad.getGraphics(), this.col, ciudad.getHeight());
        }
    }//GEN-LAST:event_agColoniaMouseClicked

    private void agEnlaceMouseClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agColoniaMouseClicked
        String name1 = JOptionPane.showInputDialog("Ingresa el nombre de la colonia inicial del nuevo enlace, \no deja en blanco para cancelar");
        while(true){
            if (this.col.containsKey(name1) || name1.equals("")){
                break;
            }
            else{
                name1 = JOptionPane.showInputDialog("La colonia aun no existe, \ningresa otro nombre o deja en blanco para cancelar");
            }
        }
        if (!name1.equals("")){
             String name2 = JOptionPane.showInputDialog("Ingresa el nombre de la colonia final del nuevo enlace");
            while(true){
                if (this.col.containsKey(name2)){
                    break;
                }
                else{
                    name2 = JOptionPane.showInputDialog("La colonia aun no existe\ningresa otra Colonia existente");
                }
            }
            int len = Integer.parseInt(JOptionPane.showInputDialog("Ingresa la longitud del enlace"));
            while (len < 0){
                len = Integer.parseInt(JOptionPane.showInputDialog("Ingresa un valor positivo para la longitud"));
            }
            int flux = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el flujo maximo del enlace"));
            while (flux < 0){
                flux = Integer.parseInt(JOptionPane.showInputDialog("Ingresa un flujo positivo para el enlace"));
            }
            this.g.agregarEnlace(name1, name2, Integer.toString(len), Integer.toString(flux));
            this.algo.add_road(name1, name2, len,flux);
            this.col = this.algo.get_deleted_nodes();
            this.en = this.algo.get_deleted_Roads();
            this.d.dibujaEnlaces(this.ciudad.getGraphics(), this.col, this.en, ciudad.getHeight());
        }
    }//GEN-LAST:event_agColoniaMouseClicked

     private void elEnlaceMouseClicked(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agColoniaMouseClicked
        String name1 = JOptionPane.showInputDialog("Ingresa el nombre de la colonia inicial del enlace a eliminar\no deja en blanco para cancelar");
        while(true){
            if (this.col.containsKey(name1) || name1.equals("")){
                break;
            }
            else{
                name1 = JOptionPane.showInputDialog("La colonia no existe,ingresa otro nombre \no deja en blanco para cancelar");
            }
        }
        if (!name1.equals("")){
             String name2 = JOptionPane.showInputDialog("Ingresa el nombre de la colonia final del enlace a eliminar");
            while(true){
                if (this.col.containsKey(name2)){
                    break;
                }
                else{
                    name2 = JOptionPane.showInputDialog("La colonia aun no existe\ningresa otra Colonia existente");
                }
            }
            this.g.eliminarEnlace(name1, name2);
            this.algo.remove_road(name1, name2);
            this.col = this.algo.get_deleted_nodes();
            this.en = this.algo.get_deleted_Roads();
            this.d.updateGrafoGeneral(ciudad.getGraphics(), ciudad.getHeight(), this.centra, this.col, this.en, ciudad.getWidth());
            this.d.dibujaCentrales(this.ciudad.getGraphics(), ciudad.getHeight(), this.centra);
            this.d.dibujaEnlaces(this.ciudad.getGraphics(),this.col,this.en, ciudad.getHeight());
            this.d.dibujaColonias(this.ciudad.getGraphics(), this.col, ciudad.getHeight());
        }
    }//GEN-LAST:event_agColoniaMouseClicked

    private void rutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rutaActionPerformed

        HashMap<String, Boolean> caminos;
        this.d.updateGrafoGeneral(ciudad.getGraphics(), ciudad.getHeight(), this.centra, this.col, this.en, ciudad.getWidth());
        this.d.dibujaCentrales(this.ciudad.getGraphics(), ciudad.getHeight(), this.centra);
        this.d.dibujaEnlaces(this.ciudad.getGraphics(),this.col,this.en, ciudad.getHeight());
        this.d.dibujaColonias(this.ciudad.getGraphics(), this.col, ciudad.getHeight());

        caminos = this.algo.get_Hamilton();
        for(Side x : this.algo.existing_roads){
            if(x.activeHamilton){
                x.print_Side();
            }
        }

        this.d.dibujaHamilton(ciudad.getGraphics(), this.col, ciudad.getHeight(), caminos);
    }//GEN-LAST:event_rutaActionPerformed

    private void centralesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_centralesActionPerformed
        this.algo.show_clusters();
    }//GEN-LAST:event_centralesActionPerformed

    private void flujoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flujoActionPerformed
        
        String co = JOptionPane.showInputDialog("Ingresa la colonia inicial");
        while(true){
            if (this.col.containsKey(co)){
                break;
            }
            else{
                co = JOptionPane.showInputDialog("Ingresa una colonia inicial existente");
            }
        }
        String cf = JOptionPane.showInputDialog("Ingresa la colonia final");
        while(true){
            if (this.col.containsKey(cf) && !co.equals(cf)){
                break;
            }
            else{
                cf = JOptionPane.showInputDialog("Ingresa una colonia final existente");
            }
        }
        
        HashMap<String, int[]> caminos;
        caminos = algo.get_Flux_detailed(co,cf);
        JOptionPane.showMessageDialog(this, "Flujo maximo: " + this.algo.get_max_Flux_Value(co, cf));
        this.d.updateGrafoGeneral(ciudad.getGraphics(), ciudad.getHeight(), this.centra, this.col, this.en, ciudad.getWidth());
        this.d.dibujaCentrales(this.ciudad.getGraphics(), ciudad.getHeight(), this.centra);
        this.d.dibujaEnlaces(this.ciudad.getGraphics(),this.col,this.en, ciudad.getHeight());
        this.d.dibujaColonias(this.ciudad.getGraphics(), this.col, ciudad.getHeight());
        this.d.dibujaFlujo(ciudad.getGraphics(), this.col, ciudad.getHeight(), caminos);

    }//GEN-LAST:event_flujoActionPerformed

    private void rutaSpanningTree(java.awt.event.ActionEvent evt){

        //ciudad.removeAll();
        //ciudad.repaint();

        HashMap<String, Boolean> caminos;
        caminos = algo.get_Span();
        this.d.updateGrafoGeneral(ciudad.getGraphics(), ciudad.getHeight(), this.centra, this.col, this.en, ciudad.getWidth());
        this.d.dibujaCentrales(this.ciudad.getGraphics(), ciudad.getHeight(), this.centra);
        this.d.dibujaEnlaces(this.ciudad.getGraphics(),this.col,this.en, ciudad.getHeight());           
        this.d.dibujaColonias(this.ciudad.getGraphics(), this.col, ciudad.getHeight());
        this.d.dibujaSpanningTree(ciudad.getGraphics(), this.col, ciudad.getHeight(), caminos);
    }

    private void guardarActionPerformed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        this.g.fill_other_date(this.algo.get_deleted_nodes(), this.algo.get_nodes_strings(), this.algo.get_deleted_Roads());
        this.g.save_current_graph();
        JOptionPane.showMessageDialog(this, "Guardado con exito");

    }//GEN-LAST:event_guardarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem agColonia;
    private javax.swing.JMenuItem agEnlace;
    private javax.swing.JMenu agregar;
    private javax.swing.JMenu borrar;
    private javax.swing.JMenuItem cableado;
    private javax.swing.JMenu cambiar;
    private javax.swing.JMenu cargar;
    private javax.swing.JMenuItem centrales;
    private javax.swing.JPanel ciudad;
    private javax.swing.JMenuItem elColonia;
    private javax.swing.JMenuItem elEnlace;
    private javax.swing.JMenuItem flujo;
    private javax.swing.JMenu guardar;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu mostrar;
    private javax.swing.JMenuItem ruta;
    // End of variables declaration//GEN-END:variables
}
