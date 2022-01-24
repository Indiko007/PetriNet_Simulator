package utilities.listeners;

import generated.ArcType;
import graphics.Arc2D;
import graphics.BasicArc2D;
import graphics.Drawable;
import graphics.ResetArc2D;
import graphics.petriNetGUI.PetriCanvas;
import petriNet.PetriNet;
import petriNet.petriNetExceptions.VertexDoesNotExistException;
import petriNet.petriNetObjects.*;
import utilities.EndPointType;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.InputMismatchException;

public class AddArcListener extends MouseAdapter {
    private final PetriCanvas canvas;
    private MouseEvent firstClick;
    private MouseEvent secondClick;
    private PetriNet pNet;
    private final ArcType arcType;

    public AddArcListener(PetriCanvas canvas, ArcType arcType) {
        this.canvas = canvas;
        pNet = canvas.getPetriNet();
        firstClick = null;
        secondClick = null;
        this.arcType = arcType;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(checkClicks(e)) {
            try {
                Drawable from = getElementFromMouse(firstClick.getPoint());
                Drawable to =  getElementFromMouse(secondClick.getPoint());
                Arc arc = getArc(from.getID(), to.getID(), arcType);
                Arc2D a2D = getArc2D(from, to, arc, arcType);
                pNet.add(arc);
                canvas.addElement(a2D, true); // TODO:
            } catch (InputMismatchException | VertexDoesNotExistException | IllegalArgumentException exception) {
                System.out.println("This arc could not be created!");
            }
            firstClick = null;
            secondClick = null;
        }
    }

    private Arc getArc(int sourceID, int destinationID, ArcType arcType) {
        Arc newArc;
        Place place = getPlaceFromArc(sourceID, destinationID);
        Transition transition = getTransitionFromArc(sourceID, destinationID);

        if(pNet.getPlaces().containsKey(sourceID)) {
            if(arcType == ArcType.RESET){
                newArc = new ResetArc(canvas.generateID(), place, transition);
            }
            else{
                newArc = new BasicArc(canvas.generateID(), place, transition, 1);
            }
        }
        else {
            if(arcType == ArcType.REGULAR) {
                newArc = new BasicArc(canvas.generateID(), transition, place, 1);
            }
            else {
                throw new InputMismatchException(); // TODO: mozno vlastnu exceptionu treba
            }
        }
        return newArc;
    }

    private Arc2D getArc2D(Drawable from, Drawable to, Arc arc, ArcType arcType) {
        Arc2D a2D;
        if (arcType == ArcType.REGULAR) {
            a2D = new BasicArc2D(from.getPosition(), to.getPosition(), arc, getEndPointType(arc));
        } else {
            a2D = new ResetArc2D(from.getPosition(), to.getPosition(), arc);
        }
        return a2D;
    }

    private Transition getTransitionFromArc(int sourceId, int destinationId) {
        Transition transition = (pNet.getTransitions().keySet().contains(sourceId)) ? pNet.getTransitions().get(sourceId) : pNet.getTransitions().get(destinationId);
        if(transition == null) {
            throw new VertexDoesNotExistException();
        }
        return transition;
    }

    private Place getPlaceFromArc(int sourceId, int destinationId) {
        Place place = (pNet.getPlaces().keySet().contains(sourceId)) ? pNet.getPlaces().get(sourceId) : pNet.getPlaces().get(destinationId);
        if(place == null) {
            throw new VertexDoesNotExistException();
        }
        return place;
    }

    private Drawable getElementFromMouse(Point point) {
        for (Drawable element : canvas.getElements()) {
            if(element.contains(point)){
                return element;
            }
        }
        throw new IllegalArgumentException();
    }

    private EndPointType getEndPointType(Arc a) {
        return (pNet.getPlaces().containsKey(a.getEndPointID())) ? EndPointType.PLACE : EndPointType.TRANSITION;
    }

    private boolean checkClicks(MouseEvent e) {
        if(firstClick == null && secondClick == null) {
            if(checkObjectsAtPosition(e)) {
                firstClick = e;
                System.out.println("First click recorded at " + e.getPoint());
            } else {
                System.out.println("You have to choose an object!");
            }
            return false;
        }
        else if (firstClick != null && secondClick == null) {
            if(checkObjectsAtPosition(e)) {
                secondClick = e;
                System.out.println("Second click recorded at " + e.getPoint());
            }
            else {
                firstClick = null;
                secondClick = null;
                System.out.println("Clicks reset!");
                return false;
            }
        }
        return true;
    }

    private boolean checkObjectsAtPosition(MouseEvent e) {
        for (Drawable element : canvas.getElements()) {
            if(element.contains(e.getPoint())){
                return true;
            }
        }
        return false;
    }
}
