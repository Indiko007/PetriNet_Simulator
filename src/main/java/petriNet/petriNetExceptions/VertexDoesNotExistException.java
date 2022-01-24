package petriNet.petriNetExceptions;

public class VertexDoesNotExistException extends RuntimeException {
    public VertexDoesNotExistException() {
        super("Given vertex does not exist!");
    }
}
