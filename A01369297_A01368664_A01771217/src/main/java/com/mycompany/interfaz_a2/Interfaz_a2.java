
package com.mycompany.interfaz_a2;

import gui.Pantalla;

public class Interfaz_a2 {

    public static void main(String[] args) {
        
        Pantalla plano = new Pantalla();
        plano.setVisible(true);
        plano.setExtendedState(Pantalla.MAXIMIZED_BOTH);
        plano.setLocationRelativeTo(null);
        
    }
}
