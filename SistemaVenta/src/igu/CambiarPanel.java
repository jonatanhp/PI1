/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igu;

import javax.swing.JPanel;

/**
 *
 * @author jonatan
 */
public class CambiarPanel {
     private JPanel panelActual;
    private JPanel nuevoPanel;

    public CambiarPanel(JPanel panelActual, JPanel nuevoPanel) {
        this.panelActual = panelActual;
        this.nuevoPanel = nuevoPanel;
        this.panelActual.removeAll();
        this.panelActual.revalidate();
        this.panelActual.repaint();
        
        this.panelActual.add(this.nuevoPanel);
        this.panelActual.revalidate();
        this.panelActual.repaint();
    }
}
