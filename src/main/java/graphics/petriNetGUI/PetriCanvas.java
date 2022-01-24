package graphics.petriNetGUI;

import graphics.*;
import petriNet.PetriNet;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;
import java.awt.*;

public class PetriCanvas extends Canvas {
    private List<Drawable> elements;
    private PetriNet pNet;
    private int lastID;

    public PetriCanvas() {
        super();
        elements = new ArrayList<>();
        pNet = new PetriNet();
        lastID = 0;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setStroke(new BasicStroke(2));
        for (Drawable element : elements) {
            element.draw2D(g2d);
        }
        g2d.dispose();
    }

    public void setMouseListener(MouseAdapter mouseAdapter){
        removeMouseListeners();
        addMouseListener(mouseAdapter);
    }

    private void removeMouseListeners() {
        for (MouseListener mouseListener : getMouseListeners()) {
            removeMouseListener(mouseListener);
        }
    }

    public void setElements(List<Drawable> elements) {
        this.elements = elements;
        lastID = updateID();
    }

    public List<Drawable> getElements() {
        return elements;
    }

    public void addElement(Drawable element, boolean addFirst) {
        if (addFirst) {
            elements.add(0, element);
        } else {
            elements.add(element);
        }
        repaint();
    }

    public void removeElement(Drawable element) {
        elements.remove(element);
    }

    public PetriNet getPetriNet(){
        return pNet;
    }

    public void setPetriNet(PetriNet pNet) {
        this.pNet = pNet;
    }

    private int updateID() {
        int highest = 0;
        for (Drawable element : elements) {
            int newID = element.getID();
            if(newID > highest) {
                highest = newID;
            }
        }
        return ++highest;
    }

    public int generateID() {
        return lastID++;
    }
}
