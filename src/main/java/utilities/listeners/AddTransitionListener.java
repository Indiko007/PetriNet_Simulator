package utilities.listeners;

import graphics.Transition2D;
import graphics.petriNetGUI.PetriCanvas;
import petriNet.PetriNet;
import petriNet.petriNetObjects.Transition;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddTransitionListener extends MouseAdapter {
    private final PetriCanvas canvas;

    public AddTransitionListener(PetriCanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        PetriNet pNet = canvas.getPetriNet();
        Transition transition = new Transition();
        transition.setID(canvas.generateID());
        pNet.add(transition);
        canvas.addElement(new Transition2D(e.getX(), e.getY(), transition), false);
    }
}
