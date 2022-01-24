package petriNet.petriNetExceptions;

public class TransitionNotFirableException extends RuntimeException {
    public TransitionNotFirableException(String errorMessage) {
        super(errorMessage);
    }
}
