
package com.mycompany.interfaz_a2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dibuja {
    
    private static void creaNodo(Graphics n, int x, int y, int w){
        n.setColor(Color.red);
        n.fillOval(x, y, w, w);
    }
    
    private static void creaCol(Graphics n, int x, int y, int w, String name){
        n.setColor(Color.BLUE);
        n.fillOval(x-5, y-5, w, w);
        n.drawString(name, x+6, y-6);
    }
    
    private static void creaEnlace(Graphics n, int x1, int y1, int x2, int y2, String dist, String capa){
        n.drawLine(x1, y1, x2, y2);
    }
    
    public void dibujaHamilton(Graphics n, HashMap<String, long[]> colonias, int hplano, HashMap <String, Boolean> cam){
        System.out.println("Hamilton");
        for (Map.Entry<String, Boolean> entry : cam.entrySet()){
            String key = entry.getKey();
            
            String elem[];
            elem = key.split("-");
            
            long coord[] = colonias.get(elem[0]);
            long coord2[] = colonias.get(elem[1]);
            
            int y1 = hplano - Long.valueOf(coord[1]).intValue();
            int y2 = hplano - Long.valueOf(coord2[1]).intValue();
            
            boolean colores = entry.getValue();
            
            if(colores == true){
                System.out.println("drawn");
                Graphics2D g2d = (Graphics2D) n;
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(5));
                g2d.drawLine(Long.valueOf(coord[0]).intValue(), y1, Long.valueOf(coord2[0]).intValue(), y2);
            }else{
                System.out.println("not drawn");
                Graphics2D g2d = (Graphics2D) n;
                g2d.setColor(Color.GRAY);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine(Long.valueOf(coord[0]).intValue(), y1, Long.valueOf(coord2[0]).intValue(), y2);
                
            }
        }
    }
    
    public void dibujaColonias(Graphics n, HashMap<String, long[]> colonias, int hplano){
        for(Map.Entry<String, long[]> entry : colonias.entrySet()){
            String key = entry.getKey();
            long[] valores = entry.getValue();
            
            int y = hplano - Long.valueOf(valores[1]).intValue();
            
            Dibuja.creaCol(n, Long.valueOf(valores[0]).intValue(), y, 13, key);
            
        }
    }
    public void dibujaColoniaUnica(Graphics n, String name, long[] valores, int hplano){
            
        int y = hplano - Long.valueOf(valores[1]).intValue();
            
        Dibuja.creaCol(n, Long.valueOf(valores[0]).intValue(), y, 13, name);
    }
    
    public void dibujaEnlaces(Graphics n, HashMap<String, long[]> colonias, ArrayList<String> enlaces, int hplano){
        
        for(int i = 0; i<enlaces.size(); i+=4){
            String claveI = enlaces.get(i);
            String claveF = enlaces.get(i+1);      
            
            long coord[] = colonias.get(claveI);
            long coord2[] = colonias.get(claveF);
            
            int y1 = hplano - Long.valueOf(coord[1]).intValue();
            int y2 = hplano - Long.valueOf(coord2[1]).intValue();
            
            Dibuja.creaEnlace(n, Long.valueOf(coord[0]).intValue(), y1, Long.valueOf(coord2[0]).intValue(), y2, enlaces.get(i+2), enlaces.get(i+3));
            
        } 
    }
    
    public void dibujaCentrales(Graphics n, int hplano, ArrayList<int[]> centra){
        
        for (int i = 0; i < centra.size(); i++) {
            int y = hplano - centra.get(i)[1];
            Dibuja.creaNodo(n, centra.get(i)[0], y, 8);
        }
        
    }
    
    public void dibujaFlujo(Graphics n, HashMap<String, long[]> colonias, int hplano, HashMap <String, int[]> cam){
 
        for (Map.Entry<String, int[]> entry : cam.entrySet()){
            String key = entry.getKey();
            
            String elem[];
            elem = key.split("-");
            
            long coord[] = colonias.get(elem[0]);
            long coord2[] = colonias.get(elem[1]);
            
            int y1 = hplano - Long.valueOf(coord[1]).intValue();
            int y2 = hplano - Long.valueOf(coord2[1]).intValue();
            
            int[] colores = entry.getValue();
            
            if(colores[0] == 1){
                Graphics2D g2d = (Graphics2D) n;
                if(colores[2]/colores[1] < 0.1){
                    g2d.setColor(Color.BLUE);
                    g2d.setStroke(new BasicStroke(3));
                    g2d.drawLine(Long.valueOf(coord[0]).intValue(), y1, Long.valueOf(coord2[0]).intValue(), y2);
                }
                else{
                    if(colores[2]/colores[1] < 0.2){
                        g2d.setColor(Color.BLUE);
                        g2d.setStroke(new BasicStroke(4));
                        g2d.drawLine(Long.valueOf(coord[0]).intValue(), y1, Long.valueOf(coord2[0]).intValue(), y2);
                    }
                    else{
                        if(colores[2]/colores[1] < 0.3){
                            g2d.setColor(Color.BLUE);
                            g2d.setStroke(new BasicStroke(5));
                            g2d.drawLine(Long.valueOf(coord[0]).intValue(), y1, Long.valueOf(coord2[0]).intValue(), y2);
                        }
                        else{
                            if(colores[2]/colores[1] < 0.4){
                                g2d.setColor(Color.BLUE);
                                g2d.setStroke(new BasicStroke(6));
                                g2d.drawLine(Long.valueOf(coord[0]).intValue(), y1, Long.valueOf(coord2[0]).intValue(), y2);
                            }
                            else{
                                if(colores[2]/colores[1] < 0.5){
                                    g2d.setColor(Color.BLUE);
                                    g2d.setStroke(new BasicStroke(7));
                                    g2d.drawLine(Long.valueOf(coord[0]).intValue(), y1, Long.valueOf(coord2[0]).intValue(), y2);
                                }
                                else{
                                    if(colores[2]/colores[1] < 0.6){
                                        g2d.setColor(Color.BLUE);
                                        g2d.setStroke(new BasicStroke(8));
                                        g2d.drawLine(Long.valueOf(coord[0]).intValue(), y1, Long.valueOf(coord2[0]).intValue(), y2);
                                    }
                                    else{
                                        if(colores[2]/colores[1] < 0.7){
                                            g2d.setColor(Color.BLUE);
                                            g2d.setStroke(new BasicStroke(9));
                                            g2d.drawLine(Long.valueOf(coord[0]).intValue(), y1, Long.valueOf(coord2[0]).intValue(), y2);
                                        }
                                        else{
                                            if(colores[2]/colores[1] < 0.8){
                                                g2d.setColor(Color.BLUE);
                                                g2d.setStroke(new BasicStroke(10));
                                                g2d.drawLine(Long.valueOf(coord[0]).intValue(), y1, Long.valueOf(coord2[0]).intValue(), y2);
                                            }
                                            else{
                                                if(colores[2]/colores[1] < 0.9){
                                                    g2d.setColor(Color.BLUE);
                                                    g2d.setStroke(new BasicStroke(11));
                                                    g2d.drawLine(Long.valueOf(coord[0]).intValue(), y1, Long.valueOf(coord2[0]).intValue(), y2);
                                                }
                                                else{
                                                    g2d.setColor(Color.BLUE);
                                                    g2d.setStroke(new BasicStroke(12));
                                                    g2d.drawLine(Long.valueOf(coord[0]).intValue(), y1, Long.valueOf(coord2[0]).intValue(), y2);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }else{
                Graphics2D g2d = (Graphics2D) n;
                g2d.setColor(Color.GRAY);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine(Long.valueOf(coord[0]).intValue(), y1, Long.valueOf(coord2[0]).intValue(), y2);
                
            }
        }
    }
    
    public void dibujaSpanningTree(Graphics n, HashMap<String, long[]> colonias, int hplano, HashMap <String, Boolean> cam){
        for (Map.Entry<String, Boolean> entry : cam.entrySet()){
            String key = entry.getKey();

            String elem[];
            elem = key.split("-");

            long coord[] = colonias.get(elem[0]);
            long coord2[] = colonias.get(elem[1]);

            int y1 = hplano - Long.valueOf(coord[1]).intValue();
            int y2 = hplano - Long.valueOf(coord2[1]).intValue();

            boolean colores = entry.getValue();

            if(colores == true){
                Graphics2D g2d = (Graphics2D) n;
                g2d.setColor(Color.green);
                g2d.setStroke(new BasicStroke(5));
                g2d.drawLine(Long.valueOf(coord[0]).intValue(), y1, Long.valueOf(coord2[0]).intValue(), y2);
                //n.setColor(Color.ORANGE);
                //n.drawLine(Long.valueOf(coord[0]).intValue(), y1, Long.valueOf(coord2[0]).intValue(), y2);
            }else{
                Graphics2D g2d = (Graphics2D) n;
                g2d.setColor(Color.GRAY);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine(Long.valueOf(coord[0]).intValue(), y1, Long.valueOf(coord2[0]).intValue(), y2);

            }
        }
    }

    
    public void updateGrafoGeneral(Graphics n, int altura, ArrayList<int[]> centrales, HashMap<String, long[]> colonias, ArrayList<String> enlaces, int w){       
        n.clearRect(0, 0, w, altura);

    }
}
