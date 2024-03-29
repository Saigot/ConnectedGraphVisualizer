/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webmaker;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author michael
 */
public class Node {

    String name;
    String Desc;
    int index;
    ArrayList<Integer> links = new ArrayList(); //sorted list
    double netForcex;
    double netForcey;
    double magnitude;
    double tension;
    double x;
    double y;
    boolean expanded;
    private boolean selected = false;
            
    static double centerX = 250;
    static double centerY = 250;
    static double centralTendency = 1;
    static double forceCap = 80;
    static double ColourScale = 1;
    static int maxRenderSize = 100;
    static int minRenderSize = 5;
    public Node(int i, String n, String d, double mag, double tens, int X, int Y, int... l) {
        x = X;
        y = Y;
        index = i;
        name = n;
        Desc = d;
        for (int k : l) {
            links.add(new Integer(k));
        }
        magnitude = Math.max(mag,1);
        tension = tens;
        netForcex = 0;
        netForcey = 0;
    }

    public void Render(Graphics g, ArrayList<Node> gl) {
        FindForce(gl);
        Color c = g.getColor();
        move();
        double size = getSize();
        if(selected){
            double r = 1.5;
            g.setColor(new Color(255, (int)(255-tension*25),(int)(255-tension*25),(int)(255*0.7)));
            g.fillOval((int) (x - (size*r/2)), (int) (y - (size*r/2)),(int) (size*r), (int) (size*r));
        }
        g.setColor(new Color(255, (int)(255-tension*25),(int)(255-tension*25)));
        g.fillOval((int) (x - (size/2)), (int) (y - (size/2)),(int) size, (int) size);
        if (!links.isEmpty()) {
            for (Node n : gl) {
                if (isLinked(n.index)) {
                    double dx = n.x - x;
                    double dy = n.y - y;
                    double distance = (double) Math.sqrt(dx * dx + dy * dy);
                    double tens = Math.abs((n.getTension(distance)) + getTension(distance) * ColourScale);
                    g.setColor(new Color(255, (int) Math.max(255 - tens, 0), (int) Math.max(255 - tens, 0)));
                    g.drawLine((int) x, (int) y, (int) n.x, (int) n.y);
                    //System.out.println((int)x + "," + (int)y + "," + (int)n.x + "," + (int)n.y);
                }

            }
        }
        
        g.setColor(c);
    }

    public void FindForce(ArrayList<Node> gl) {
        netForcex = 0;
        netForcey = 0;
        for (Node n : gl) {
            if (n == this) {
                continue;
            }
            double dx = n.x - x;
            double dy = n.y - y;
            double distance = (double) Math.sqrt(dx*dx+dy*dy);
            double netforcem;
            netforcem = -n.getMagnitude(distance);
            if (n.isLinked(index) || isLinked(n.index)) {
                netforcem += (n.getTension(distance)) + getTension(distance) /2;
            }
            distance = distance == 0 ? 0.0001f : distance;
            netForcex += dx / distance * netforcem;
            netForcey += dy / distance * netforcem;


        }

        //central force
        double distance = (double) (Math.sqrt((centerX - x)*(centerX - x)+(centerY - y)*(centerY - y)));
        double netForcem = (double) (centralTendency * Math.expm1(distance 
                / Math.sqrt(centerX*centerX+centerY*centerY)));
        distance = distance <= 0.000001? 0.000001:distance;
        netForcex += (centerX - x) * netForcem / distance * magnitude;
        netForcey += (centerY - y) * netForcem / distance * magnitude;
        //netForcex+=(((Math.exp(centerX - x))*centralTendency)/Math.exp(centerX));
        //netForcex += (((centerX*centerX - x*x)*centralTendency)/(centerX*centerX)); 
        //netForcey += (((centerY*centerY - y*y)*centralTendency)/(centerY*centerY));

    }

    public void move() {
        double oldx = x;
        double oldy = y;
        netForcex = Math.min(Math.abs(netForcex), forceCap)*Math.signum(netForcex);
        netForcey = Math.min(Math.abs(netForcey), forceCap)*Math.signum(netForcey);
        x += netForcex / magnitude;
        y += netForcey / magnitude;

        if (Math.abs(x) > Double.MAX_VALUE / 3) {
            x = Double.MAX_VALUE / 3 * Math.signum(x);
        }
        if (Math.abs(y) > Double.MAX_VALUE / 3) {
            y = Double.MAX_VALUE / 3 * Math.signum(y);
        }
        if (Double.isInfinite(x) || Double.isNaN(x)) {
            System.out.println("ERROR" + index);
            x = oldx; // do nothing and hope things get fixed
        }
        if (Double.isInfinite(y) || Double.isNaN(y)) {
            System.out.println("ERROR:" + index);
            y = oldy; // do nothing and hope things get fixed
        }
        if (Desc.equals("LOG14295")) {
            System.out.format("X: %f/%f = %f\n", netForcex, magnitude, x);
            System.out.format("Y: %f/%f = %f\n", netForcey, magnitude, y);
        }
    }

    public boolean isLinked(int index) {
        if (links == null) {
            return false;
        }
        return !(links.indexOf(index) == -1);
    }
    
    private double getSize(){
        return Math.min(Math.max(magnitude / 4, minRenderSize), maxRenderSize);
    }

    public double getTension(double distance) {
        return tension * distance / 20;
    }
    public boolean isClicked(int X, int Y){
        return (Math.abs(x-X) < getSize()/2 && Math.abs(y-Y) < getSize()/2);
    }
    
    public void link(int i){
        if(!isLinked(i)){
            links.add(new Integer(i));
        }
    }
    public void toggleSelect(){
        selected = !selected;
    }
    public void unSelect(){
        selected = false;
    }
    public void Select(){
        selected = true;
    }
    public double getMagnitude(double distance) {
        distance = distance * distance <= 0.00001f ? 0.00001f : distance; //prevents infiniity problems
        //squaring makes the distance stuff more realistic
        return (magnitude * magnitude) / (distance * distance) * 2;
    }
}
