/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NetworkMod;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Cluster {

    public ArrayList<int[]> centrales = new ArrayList<>();
    public ArrayList<Node> colonias = new ArrayList<>();
    public int mapx=700;
    public int mapy=700;

    Cluster(ArrayList<int[]> central, ArrayList<Node> col, int[] dimensiones) {
        this.centrales = central;
        this.colonias = col;
        this.mapx = dimensiones[0];
        this.mapy = dimensiones[1];
        System.out.println(dimensiones);
    }

    public double distanciaEuclidiana(int[] punto1, int[] punto2) {
        return Math.sqrt(Math.pow(punto1[0] - punto2[0], 2) + Math.pow(punto1[1] - punto2[1], 2));
    }

    public int encontrarsitiomascercano(int[] punto) {
        double[] distancias = new double[this.centrales.size()];

        for (int i = 0; i < this.centrales.size(); i++) {
            distancias[i] = distanciaEuclidiana(punto, this.centrales.get(i));
        }
        return puntoAlQuePertenece(distancias);
    }

    public int puntoAlQuePertenece(double[] array) {

        int punto_mas_cercano = 0;
        double valorMinimo = array[0];
        @SuppressWarnings("unused")
        int cont = 1;

        for (int i = 1; i < array.length; i++) {
            if (array[i] < valorMinimo) {
                valorMinimo = array[i];
                punto_mas_cercano = i;
            } else if (Math.abs(array[i] - valorMinimo) < 1e-10) {
                cont++;
            }
        }
        return punto_mas_cercano;
    }

    public int[][] colocarValorPixeles( int ancho, int alto) {
        int[][] diagrama = new int[alto][ancho];

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int[] punto = {x, y};
                int sitioMasCercano = encontrarsitiomascercano(punto);
                diagrama[y][x] = sitioMasCercano + 1;
            }
        }

        //Muestra el valor del punto al que pertenece el pixel
        /*
        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                System.out.print(diagrama[y][x]+" ");
            }
            System.out.println("");
        }*/
        return diagrama;
    }

    public BufferedImage colocarInformacionImagen(int ancho, int alto) {
        int[][] diagrama = colocarValorPixeles(ancho, alto);

        BufferedImage imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        Graphics g = imagen.getGraphics();

        for (int y = alto-1; y >= 0; y--) {
            for (int x = 0; x < ancho; x++) {
                int ValordelColor = diagrama[y][x] * 255 / this.centrales.size();
                //System.out.print(ValordelColor+" ");
                Color color = new Color(ValordelColor, (ValordelColor * 2) % 255, (ValordelColor * 3) % 255);
                g.setColor(color);
                g.fillRect(x, alto-1-y, 1,1);
            }
            //System.out.println(" ");
        }

        g.setColor(Color.BLACK);
        for (int[] sitio : this.centrales) {
            g.fillOval(sitio[0] - 5, alto - 1 - (sitio[1] - 5), 10, 10);
        }

        g.setColor(Color.blue);
        for(Node colonia: this.colonias){
            g.fillOval(colonia.coordx-5,alto - 1 - (colonia.coordy-5),10,10);
        }

        return imagen;
    }

    public void show_map() {
        BufferedImage imagenVoronoi = colocarInformacionImagen(this.mapx, this.mapy);

        JFrame marco = new JFrame("Voronoi");
        marco.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        marco.getContentPane().add(new JLabel(new ImageIcon(imagenVoronoi)));
        marco.pack();
        marco.setLocationRelativeTo(null);
        marco.setVisible(true);
    }
}