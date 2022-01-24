package utilities.listeners;

import graphics.Place2D;
import graphics.petriNetGUI.PetriCanvas;
import petriNet.PetriNet;
import petriNet.petriNetObjects.Place;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddPlaceListener extends MouseAdapter {

    private final PetriCanvas canvas;

    public AddPlaceListener(PetriCanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        PetriNet pNet = canvas.getPetriNet();
        Place place = new Place();
        place.setID(canvas.generateID());
        pNet.add(place);
        canvas.addElement(new Place2D(e.getX(), e.getY(), place), false);
    }
}
