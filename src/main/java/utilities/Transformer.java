package utilities;

import generated.Document;
import petriNet.petriNetExceptions.ArcWeigthOutOfBoundsException;

public abstract class Transformer<T> {

    public abstract T transform(Document document);

    abstract void transformTransitions();

    abstract void transformPlaces();

    abstract void transformArcs() throws ArcWeigthOutOfBoundsException;

}
