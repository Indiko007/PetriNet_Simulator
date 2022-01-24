package petriNet.petriNetObjects;

import petriNet.petriNetExceptions.TransitionNotFirableException;

import java.util.HashMap;

public final class Transition extends Vertex {
    private int transitionID;
    private static int transitionCount = 0;
    private String name;

    public Transition() {
        this.name = "transition" + transitionCount;
        this.transitionID = generateObjectID();
        transitionCount++;
    }

    public Transition(String name) {
        this();
        this.name = name;
    }

    public int getID() {
        return this.transitionID;
    }

    public void setID(int transitionID) {
        this.transitionID = transitionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void checkFireability() throws TransitionNotFirableException {
        HashMap<Integer, Integer> totalWeights = new HashMap<>();
        for(Arc arc: this.getIncomingArcs()) {

            Place place = places.get(arc.getStartPointID());
            int newWeight = arc.getWeight();
            if (totalWeights.putIfAbsent(place.getID(), newWeight) != null) {
                newWeight += totalWeights.get(place.getID());
            }
            totalWeights.put(place.getID(), newWeight);
        }
        for(Arc arc: this.getIncomingArcs()) {
            if(totalWeights.get(arc.getStartPointID()) > places.get(arc.getStartPointID()).getTokens()) {
                throw new TransitionNotFirableException("Transition cannot be fired! \r\nWeight count of outgoing arcs is greater than number of tokens");
            }
        }
    }

    public boolean canFire(){
        try {
            this.checkFireability();
        } catch(TransitionNotFirableException exception) {
            return false;
        }
        return true;
    }
}
