package utilities.listeners;

import graphics.Arc2D;
import graphics.Drawable;
import graphics.petriNetGUI.PetriCanvas;
import petriNet.PetriNet;
import petriNet.petriNetObjects.Arc;
import petriNet.petriNetObjects.Vertex;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class RemoveItemListener extends MouseAdapter {
    private final PetriCanvas canvas;
    private final PetriNet pNet;

    public RemoveItemListener(PetriCanvas canvas) {
        this.canvas = canvas;
        pNet = canvas.getPetriNet();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Drawable toRemove = null;
        try {
            toRemove = getElementFromMouse(e.getPoint());
            removeAdjustedArcs(toRemove);
        } catch(IllegalArgumentException exception) {
            List<Arc2D> arcs = getDrawableArcsList();
            for (Arc2D arc : arcs) {
                if(arc.intersects(new Rectangle2D.Float(e.getX()-10, e.getY()-10, 10, 10))){ // TODO: Mozno vytvorit vlastnu metodu na to
                    toRemove = arc;
                }
            }
        }
        if(toRemove != null) {
            removeFromEverywhere(toRemove);
            canvas.repaint();
        }
    }

    private void removeFromEverywhere(Drawable toRemove) {
        try {
            removePetriObject(toRemove.getID());
            canvas.removeElement(toRemove);
        } catch(IllegalArgumentException exception) {
            System.out.println("Couldn't remove this object!");
        }
    }

    private void removePetriObject(int id) {
        if(pNet.getPlaces().containsKey(id)) {
            pNet.remove(pNet.getPlaces().get(id));
        }
        else if(pNet.getTransitions().containsKey(id)) {
            pNet.remove(pNet.getTransitions().get(id));
        }
        else if(pNet.getArcs().containsKey(id)) {
            pNet.remove(pNet.getArcs().get(id));
        }
        else {
            throw new IllegalArgumentException();
        }
    }


    private void removeAdjustedArcs(Drawable vertex2D) {
        Vertex vertex = pNet.getPlaces().keySet().contains(vertex2D.getID()) ?  pNet.getPlaces().get(vertex2D.getID()) :  pNet.getTransitions().get(vertex2D.getID());
        for (Arc incomingArc : vertex.getIncomingArcs()) {
            pNet.remove(incomingArc);
        }
        for (Arc outgoingArc : vertex.getOutgoingArcs()) {
            pNet.remove(outgoingArc);
        }
        for (Arc2D arc2D : getDrawableArcsList()) {
            if(vertex2D.getID() == arc2D.getSourceID() || vertex2D.getID() == arc2D.getDestinationID()) {
                canvas.removeElement(arc2D);
            }
        }
    }

    private Drawable getElementFromMouse(Point point) {
        for (Drawable element : canvas.getElements()) {
            if(element.contains(point)){
                return element;
            }
        }
        throw new IllegalArgumentException();
    }

    private List<Arc2D> getDrawableArcsList() { // TODO: Mozno bude stacit List<Drawable>
        List<Arc2D> arcs = new ArrayList<>();
        for (Drawable element : canvas.getElements()) {
            if(element.getPosition() == null) {
                arcs.add((Arc2D)element);
            }
        }
        return arcs;
    }
}
