package petriNet.petriNetObjects;

import petriNet.PetriNet;

import java.util.ArrayList;


public abstract class Vertex extends PetriNet {
    private ArrayList<Arc> outgoingArcs = new ArrayList<>();
    private ArrayList<Arc> incomingArcs = new ArrayList<>();

    public ArrayList<Arc> getOutgoingArcs() {
        return outgoingArcs;
    }

    public ArrayList<Arc> getIncomingArcs() {
        return incomingArcs;
    }

    void addIncomingArc(Arc arc) {
        incomingArcs.add(arc);
    }

    public void removeIncomingArcs(ArrayList<Arc> arcsToDelete) { incomingArcs.removeAll(arcsToDelete); }

    void addOutgoingArc(Arc arc) {
        outgoingArcs.add(arc);
    }

    public void removeOutgoingArcs(ArrayList<Arc> arcsToDelete) { outgoingArcs.removeAll(arcsToDelete); }
}
