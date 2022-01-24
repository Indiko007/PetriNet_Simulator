package petriNet.petriNetObjects;

public final class ResetArc extends Arc {
    public ResetArc(int id, Place from, Transition to) {
        super(id, from.getID(), to.getID());
        this.weight = 0;
        from.addOutgoingArc(this);
        to.addIncomingArc(this);
    }

    @Override
    public int consumeTokens(Place place) {
        return 0;
    }

    @Override
    public int produceTokens(Place place) {
        return 0;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public void setWeight(int weight) {
        this.weight = 0;
    }

    @Override
    public boolean isReset() {
        return true;
    }
}