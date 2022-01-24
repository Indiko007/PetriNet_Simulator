package petriNet.petriNetObjects;

import petriNet.petriNetExceptions.ArcWeigthOutOfBoundsException;
import petriNet.PetriNet;

public abstract class Arc extends PetriNet {
    private int arcID;
    private int startPointID;
    private int endPointID;
    int weight; // Package private variable

    Arc(int id, int from, int to) {
//        this.arcID = generateObjectID();
        this.arcID = id;
        this.weight = 1;
        this.startPointID = from;
        this.endPointID = to;
    }

    Arc(int id, int from, int to, int weight) {
        this(id, from, to);
        if(weight<1) {
            throw new ArcWeigthOutOfBoundsException("The weight of this arc is out of bounds!");
        }
        this.weight = weight;
    }

    public int getStartPointID() {
        return startPointID;
    }

    public int getEndPointID() {
        return endPointID;
    }

    public int getID() {
        return arcID;
    }

    public void setID(int arcID) {
        this.arcID = arcID;
    }

    public abstract int consumeTokens(Place place);
    public abstract int produceTokens(Place place);

    public abstract int getWeight();
    public abstract void setWeight(int weight);

    public abstract boolean isReset();
}