/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webmaker;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author michael
 */
public class GUI implements ActionListener{
    int rate = 1;
    Timer t = new Timer(rate, this);
    Canvas c;
    JFrame form = new JFrame("Web");
    public void build(){
        
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.setSize(500, 50);
        form.setVisible(true);
        c = new Canvas();
        c.setBackground(Color.BLACK);
        c.setForeground(Color.WHITE);
        form.setExtendedState(form.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        //c.addNode("","LOG14295", 3,1,0,200);
        ConnectedNetwork(200, 0,1);
        //testGenerate(50);
        //testGenerate(500,1409365155033l);
        form.add(c);
        t.start();
        
        
    }
    
    public void SolarSystem(){
        c.addNode("", "", 5000, 5, (int)Node.centerX, (int)Node.centerY);
        c.addNode("", "", 10, 15,(int)Node.centerX - 50, (int)Node.centerY, 0);
        c.addNode("", "", 10, 1,(int)Node.centerX-50, (int)Node.centerY-50, 0);
        c.addNode("", "LOG14295", 0.3f, 10f,(int)Node.centerX - 55, (int)Node.centerY-10, 1);
        c.addNode("", "", 70, 1,(int)Node.centerX+30, (int)Node.centerY-30, 0);
    }
     public void testGenerate(int blots, long seed){
        Random rand = new Random(seed);
        System.out.println(seed);
        for(int i = 0; i < blots; i++){
            ArrayList<Integer> t = new ArrayList();
            if(rand.nextFloat() > 0.95)
            for(int j = 0; j < i; j++){
                if(rand.nextFloat() > 0.25){
                    t.add(new Integer((int)(rand.nextFloat() * i)));
                }else{
                    break;
                }
            }
            int[] ret = new int[t.size()];
            for (int k = 0; k < ret.length; k++) {
                ret[k] = t.get(k).intValue();
            }
            c.addNode("","",(double)(rand.nextFloat()*100),(double)(rand.nextFloat()*10),
                    (int)(rand.nextFloat()*form.getWidth()),(int)(rand.nextFloat()*form.getHeight()),ret
                    );
        }
    }
    public void testGenerate(int blots){
        testGenerate(blots, System.currentTimeMillis());
    }
public void ConnectedNetwork(int blots, int connects, int maxconnects, long seed){
        Random rand = new Random(seed);
        System.out.println(seed);
        for(int i = 0; i < blots; i++){
            ArrayList<Integer> t = new ArrayList();
            for(int j = 0; j < i; j++){
                if((rand.nextFloat() > 0.5 || j < connects)&& !(maxconnects < j) ){
                    t.add(new Integer((int)(rand.nextFloat() * i)));
                }else{
                    break;
                }
            }
            int[] ret = new int[t.size()];
            for (int k = 0; k < ret.length; k++) {
                ret[k] = t.get(k).intValue();
            }
            c.addNode("","",(double)(rand.nextFloat()*100),(double)(rand.nextFloat()*10),
                    (int)(rand.nextFloat()*form.getWidth()),(int)(rand.nextFloat()*form.getHeight()),ret
                    );
        }
    }
    public void ConnectedNetwork(int blots, int connectsmin,int connectsmax){
         ConnectedNetwork(blots,connectsmin,connectsmax, System.currentTimeMillis());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        c.repaint();    
    }
}
