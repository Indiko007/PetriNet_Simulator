package utilities;

import generated.Document;
import graphics.*;
import petriNet.PetriNet;
import petriNet.petriNetExceptions.ArcWeigthOutOfBoundsException;
import petriNet.petriNetObjects.Arc;
import petriNet.petriNetObjects.Place;
import petriNet.petriNetObjects.Transition;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Graphics2DTransformer extends Transformer<List<Drawable>> {
    private ArrayList<Drawable> elements = new ArrayList<>();
    private PetriNet pNet;
    private Document document;


    public Graphics2DTransformer(PetriNet pNet) {
        this.pNet = pNet;
    }

    @Override
    public ArrayList<Drawable> transform(Document document) {
        this.document = document;
        transformTransitions();
        transformPlaces();
        transformArcs();
        return elements;
    }

    @Override
    void transformTransitions() {
        List<Transition2D> transitions = new ArrayList<>();
        for (generated.Transition transition : document.getTransition()) {
            Transition newTransition = pNet.getTransitions().get(transition.getId());
            Transition2D transition2D = new Transition2D(transition.getX(), transition.getY(), newTransition);
            transitions.add(transition2D);
        }
        elements.addAll(transitions);
    }

    @Override
    void transformPlaces() {
        List<Place2D> places = new ArrayList<>();
        for (generated.Place place : document.getPlace()) {
            Place newPlace = pNet.getPlaces().get(place.getId());
            Place2D place2D = new Place2D(place.getX(), place.getY(), newPlace);
            places.add(place2D);
        }
        elements.addAll(places);
    }

    @Override
    void transformArcs() throws ArcWeigthOutOfBoundsException {
        List<Arc2D> arcs = new ArrayList<>();
        for (generated.Arc arc : document.getArc()) {
            Arc a = pNet.getArcs().get(arc.getId());
            Arc2D a2D;
            if (a.isReset()){
                a2D = new ResetArc2D(getPosition(arc.getSourceId()), getPosition(arc.getDestinationId()), a);
            }
            else {
                a2D = new BasicArc2D(getPosition(arc.getSourceId()), getPosition(arc.getDestinationId()), a, getEndPointType(a));
            }
            arcs.add(a2D);
        }
        elements.addAll(arcs);
    }

    private EndPointType getEndPointType(Arc a) {
        return (pNet.getPlaces().containsKey(a.getEndPointID())) ? EndPointType.PLACE : EndPointType.TRANSITION;
    }

    private Point getPosition(int vertexID) {
        Point point = new Point();
        for (Drawable element : elements) {
            if(element.getID() == vertexID) {
                point = element.getPosition();
            }
        }
        return point;
    }
}
