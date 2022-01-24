package utilities;

import generated.ArcType;
import generated.Document;
import petriNet.PetriNet;
import petriNet.petriNetExceptions.ArcWeigthOutOfBoundsException;
import petriNet.petriNetObjects.*;

public class PetriNetTransformer extends Transformer<PetriNet> {
    private Document document = new Document();
    private PetriNet pNet;


    public PetriNetTransformer() {
        pNet = new PetriNet();
    }

    @Override
    public PetriNet transform(Document document) {
        this.document = document;
        transformTransitions();
        transformPlaces();
        transformArcs();
        return new PetriNet();
    }

    @Override
    void transformTransitions() {
        for (generated.Transition transition : document.getTransition()) {
            Transition newTransition = new Transition(transition.getLabel());
            newTransition.setID(transition.getId());
            pNet.add(newTransition);
        }
    }

    @Override
    void transformPlaces() {
        for (generated.Place place : document.getPlace()) {
            Place newPlace = new Place(place.getLabel(), place.getTokens());
            newPlace.setID(place.getId());
            pNet.add(newPlace);
        }
    }

    @Override
    void transformArcs() throws ArcWeigthOutOfBoundsException {
        for (generated.Arc arc : document.getArc()) {
            Arc newArc;
            Place place = getPlace(arc.getSourceId(), arc.getDestinationId());
            Transition transition = getTransition(arc.getSourceId(), arc.getDestinationId());
            if(pNet.getPlaces().containsKey(arc.getSourceId())) {
                if(arc.getType() == ArcType.RESET){
                    newArc = new ResetArc(arc.getId(), place, transition);
                }
                else{
                    newArc = new BasicArc(arc.getId(), place, transition, arc.getMultiplicity());
                }
            }
            else {
                newArc = new BasicArc(arc.getId(), transition, place, arc.getMultiplicity());
            }
            pNet.add(newArc);
        }
    }
    private Transition getTransition(int sourceId, int destinationId) {
        return (pNet.getTransitions().keySet().contains(sourceId)) ? pNet.getTransitions().get(sourceId) : pNet.getTransitions().get(destinationId);
    }

    private Place getPlace(int sourceId, int destinationId) {
        return (pNet.getPlaces().keySet().contains(sourceId)) ? pNet.getPlaces().get(sourceId) : pNet.getPlaces().get(destinationId);
    }
}
