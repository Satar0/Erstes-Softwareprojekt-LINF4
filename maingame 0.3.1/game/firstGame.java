package game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;        
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

//import GUI_Logik.*;


public class firstGame {

    private List<GameListener> listeners = new ArrayList<>();

    private BufferedImage canvas;
    private DrawPanel panel;

    public firstGame(){

        JFrame myFrame = new JFrame("myGame");
        myFrame.setSize(200,200);
        myFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // <<< NEU: Panel statt direkt Frame benutzen
        panel = new DrawPanel();
        myFrame.add(panel);

        myFrame.setVisible(true);

        myFrame.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseMoved(MouseEvent e) {

                //listeners.forEach(l -> l.mousePosition(e.getX(), e.getY()));


            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // optional
            }
        });
    }

    public void addGameListener(GameListener gl){
        listeners.add(gl);
    }

    public void mousePosition(int x, int y){
        // bleibt leer oder kann später genutzt werden
    }

    // <<< NEU: Pixel dauerhaft zeichnen
    public void drawPixel(int x, int y) {
        if (canvas == null) return;

        Graphics2D g2 = canvas.createGraphics();
        g2.setColor(Color.BLACK);
        g2.fillRect(x, y, 5, 5);
        g2.dispose();

        panel.repaint();
    }

    // <<< NEU: eigenes Panel
    private class DrawPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // <<< NEU: Canvas einmalig initialisieren
            if (canvas == null) {
                canvas = new BufferedImage(
                        getWidth(),
                        getHeight(),
                        BufferedImage.TYPE_INT_RGB
                );
                Graphics2D g2 = canvas.createGraphics();
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }

            g.drawImage(canvas, 0, 0, null);
        }
    }
}
