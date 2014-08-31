/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webmaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author michael
 */
public class Canvas extends JPanel implements MouseListener{
    ArrayList<Node> gl = new ArrayList();
    Node selected = null;
    long held;
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Node.centerX = getWidth()/2;
        Node.centerY = getHeight()/2;
        for(Node n : gl){
            n.Render(g, gl);
        }
        g.setColor(Color.red);
        g.drawLine((int)Node.centerX, 0, (int)Node.centerX, (int)(2*Node.centerY));
        g.drawLine(0, (int)Node.centerY, (int)Node.centerX*2, (int)Node.centerY);
    }
    
    public void addNode(String name, String desc, double mag, double ten, int x, int y, int ... links){
        gl.add(new Node(gl.size(),name,desc,mag,ten,x,y,links));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       
    }
    

    @Override
    public void mousePressed(MouseEvent e) {
        held = System.currentTimeMillis();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println(e.getX() + "," + e.getY());
         for(Node n : gl){
            if(n.isClicked(e.getX(),e.getY())){
                if (selected == null) {
                    if (selected == n) {
                        selected = null;
                        selected.unSelect();
                    } else {
                        selected = n;
                        selected.Select();
                    }
                }else{
                    n.link(selected.index);
                    selected.unSelect();
                    selected = null;
                }
                return;
            }
        }
         //if no node is clicked add a new one
         long size = (System.currentTimeMillis()-held)/10;
         addNode("", "", size, 5, e.getX(), e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
