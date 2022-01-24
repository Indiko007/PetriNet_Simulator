package petriNet.petriNetExceptions;

public class ArcWeigthOutOfBoundsException extends RuntimeException {
    public ArcWeigthOutOfBoundsException(String errorMessage) {
        super(errorMessage);
    }
}
