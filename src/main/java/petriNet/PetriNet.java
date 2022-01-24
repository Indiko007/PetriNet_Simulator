package petriNet;



import petriNet.petriNetExceptions.TransitionNotFirableException;
import petriNet.petriNetObjects.Arc;
import petriNet.petriNetObjects.Place;
import petriNet.petriNetObjects.Transition;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class PetriNet {
    protected static LinkedHashMap<Integer, Place> places = new LinkedHashMap<>();
    private static LinkedHashMap<Integer, Arc> arcs = new LinkedHashMap<>();
    private static LinkedHashMap<Integer, Transition> transitions = new LinkedHashMap<>();

    private static int petriObjectID = 0;

    protected int generateObjectID() {
        return petriObjectID++;
    }

    // .add() method adds object into corresponding container
    public void add(Place... placeInput) {
        for (Place place : placeInput) {
            places.put(place.getID(), place);
        }
    }

    public void add(Arc... arcInput) {
        for (Arc arc : arcInput) {
            arcs.put(arc.getID(), arc);
        }
    }

    public void add(Transition... transitionInput) {
        for (Transition transition : transitionInput) {
            transitions.put(transition.getID(), transition);
        }
    }

    public void remove(Place... placeInput) {
        for (Place place : placeInput) {
            places.remove(place.getID());
            removeAttachedArcs(place);
        }
    }

    public void remove(Arc... arcInput) {
        for (Arc arc : arcInput) {
            arcs.remove(arc.getID());
            removeArcFromVertexes(arc);
        }
    }

    public void remove(Transition... transitionInput) {
        for (Transition transition : transitionInput) {
            transitions.remove(transition.getID());
            removeAttachedArcs(transition);
        }
    }

    private void removeArcFromVertexes(Arc arc) {
        for (Transition value : getTransitions().values()) {
            ArrayList<Arc> outgoingArcsToRemove = new ArrayList<>();
            ArrayList<Arc> incomingArcsToRemove = new ArrayList<>();
            for (Arc outgoingArc : value.getOutgoingArcs()) {
                if(outgoingArc.equals(arc)) {
                    outgoingArcsToRemove.add(outgoingArc);
                }
            }
            for (Arc incomingArc : value.getIncomingArcs()) {
                if(incomingArc.equals(arc)) {
                    incomingArcsToRemove.add(incomingArc);
                }
            }
            value.removeOutgoingArcs(outgoingArcsToRemove);
            value.removeIncomingArcs(incomingArcsToRemove);
        }

    }

    private void removeAttachedArcs(Place vertex) {
        ArrayList<Arc> outgoingArcsToRemove = new ArrayList<>(vertex.getOutgoingArcs());
        ArrayList<Arc> incomingArcsToRemove = new ArrayList<>(vertex.getIncomingArcs());
        for (Transition value : getTransitions().values()) {
            ArrayList<Arc> outgoingArcsFromTheOtherEndToRemove = new ArrayList<>();
            ArrayList<Arc> incomingArcsFromTheOtherEndToRemove = new ArrayList<>();
            for (Arc outgoingArc : value.getOutgoingArcs()) {
                if(outgoingArc.getStartPointID() == vertex.getID() || outgoingArc.getEndPointID() == vertex.getID()) {
                    outgoingArcsFromTheOtherEndToRemove.add(outgoingArc);
                }
            }
            for (Arc incomingArc : value.getIncomingArcs()) {
                if(incomingArc.getStartPointID() == vertex.getID() || incomingArc.getEndPointID() == vertex.getID()) {
                    incomingArcsFromTheOtherEndToRemove.add(incomingArc);
                }
            }
            value.removeOutgoingArcs(outgoingArcsFromTheOtherEndToRemove);
            value.removeIncomingArcs(incomingArcsFromTheOtherEndToRemove);
        }
        vertex.removeOutgoingArcs(outgoingArcsToRemove);
        vertex.removeIncomingArcs(incomingArcsToRemove);
    }

    private void removeAttachedArcs(Transition vertex) {
        ArrayList<Arc> outgoingArcsToRemove = new ArrayList<>(vertex.getOutgoingArcs());
        ArrayList<Arc> incomingArcsToRemove = new ArrayList<>(vertex.getIncomingArcs());
        for (Place value : getPlaces().values()) {
            ArrayList<Arc> outgoingArcsFromTheOtherEndToRemove = new ArrayList<>();
            ArrayList<Arc> incomingArcsFromTheOtherEndToRemove = new ArrayList<>();
            for (Arc outgoingArc : value.getOutgoingArcs()) {
                if(outgoingArc.getStartPointID() == vertex.getID() || outgoingArc.getEndPointID() == vertex.getID()) {
                    outgoingArcsFromTheOtherEndToRemove.add(outgoingArc);
                }
            }
            for (Arc incomingArc : value.getIncomingArcs()) {
                if(incomingArc.getStartPointID() == vertex.getID() || incomingArc.getEndPointID() == vertex.getID()) {
                    incomingArcsFromTheOtherEndToRemove.add(incomingArc);
                }
            }
            value.removeOutgoingArcs(outgoingArcsFromTheOtherEndToRemove);
            value.removeIncomingArcs(incomingArcsFromTheOtherEndToRemove);
        }
        vertex.removeOutgoingArcs(outgoingArcsToRemove);
        vertex.removeIncomingArcs(incomingArcsToRemove);
    }


    public void fire(int transitionID) {
        try {
            // Checking if given transition exists and if does then checking whether it is also fireable
            Transition transition = getTransition(transitionID);
            transition.checkFireability();
            // Reordering incoming arcs in order to fire ResetArcs last
            reorderArcs(transition.getIncomingArcs());
            // First consuming tokens via incoming arcs then producing tokens via outgoing arcs
            fireIncomingArc(transition.getIncomingArcs());
            fireOutgoingArc(transition.getOutgoingArcs());
        } catch(TransitionNotFirableException | IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private Transition getTransition(int transitionID) throws IllegalArgumentException {
        Transition transition = transitions.get(transitionID);
        if(transition == null) {
            throw new IllegalArgumentException("Given transition does not exist!");
        }
        return transition;
    }

    private void reorderArcs(ArrayList<Arc> arcList) {
        for(int i=0; i<arcList.size(); i++) {
            Arc arc = arcList.get(i);
            if(arc.isReset()){
                arcList.remove(arc);
                arcList.add(arc);
            }
        }
    }

    private void fireIncomingArc(ArrayList<Arc> incomingArcs) {
        for(Arc arc: incomingArcs) {
            Place place = places.get(arc.getStartPointID());
            if(place != null) {
                int difference = arc.consumeTokens(place);
                place.setTokens(difference);
            }
        }
    }

    private void fireOutgoingArc(ArrayList<Arc> outgoingArcs) {
        for(Arc arc: outgoingArcs) {
            Place place = places.get(arc.getEndPointID());
            if(place != null) {
                int difference = arc.produceTokens(place);
                place.setTokens(difference);
            }
        }
    }

    public void reset() {
        places = new LinkedHashMap<>();
        arcs = new LinkedHashMap<>();
        transitions = new LinkedHashMap<>();
    }

    public LinkedHashMap<Integer, Place> getPlaces() {
        return places;
    }

    public LinkedHashMap<Integer, Arc> getArcs() {
        return arcs;
    }

    public LinkedHashMap<Integer, Transition> getTransitions() {
        return transitions;
    }

    public void listPlaces() {
        System.out.println("\r\nPlaces:");
        for (Place place : places.values()) {
            System.out.println("    " + place.getName() + "[ID: " + place.getID() + "]: " + place.getTokens() + " tokens");
        }
        System.out.println();
    }

    public void listTransitions() {
        System.out.println("\r\nTransitions");
        for(Transition transition: transitions.values()) {
            System.out.println("    " + transition.getName() + " [ID: " + transition.getID() + "]");
        }
    }

    public void listArcs() {
        System.out.println("\r\nArcs:");
        for(Arc arc: arcs.values()){
            System.out.println("    a" + arc.getID() + " Weight: [" + arc.getWeight() + "]");
        }
    }
}