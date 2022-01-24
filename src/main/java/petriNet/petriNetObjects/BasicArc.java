package petriNet.petriNetObjects;

public final class BasicArc extends Arc {

    public BasicArc(int id, Place from, Transition to, int weight) {
        super(id, from.getID(), to.getID(), weight);
        from.addOutgoingArc(this);
        to.addIncomingArc(this);
    }

    public BasicArc(int id, Transition from, Place to, int weight) {
        super(id, from.getID(), to.getID(), weight);
        from.addOutgoingArc(this);
        to.addIncomingArc(this);
    }

    public BasicArc(int id, Place from, Transition to) {
        super(id, from.getID(), to.getID());
        from.addOutgoingArc(this);
        to.addIncomingArc(this);
    }

    public BasicArc(int id, Transition from, Place to) {
        super(id, from.getID(), to.getID());
        from.addOutgoingArc(this);
        to.addIncomingArc(this);
    }

    @Override
    public int consumeTokens(Place place) {
        return (place.getTokens() - this.weight);
    }

    @Override
    public int produceTokens(Place place) {
        return (place.getTokens() + this.weight);
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean isReset() {
        return false;
    }
}