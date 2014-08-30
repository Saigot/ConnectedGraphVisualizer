/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webmaker;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author michael
 */
public class Canvas extends JPanel{
    ArrayList<Node> gl = new ArrayList();
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Node.centerX = getWidth()/2;
        Node.centerY = getHeight()/2;
        for(Node n : gl){
            n.Render(g, gl);
        }
        g.setColor(Color.red);
        g.fillOval((int)Node.centerX,(int)Node.centerY,2,2);
    }
    
    public void addNode(String name, String desc, double mag, double ten, int x, int y, int ... links){
        gl.add(new Node(gl.size(),name,desc,mag,ten,x,y,links));
    }
}
