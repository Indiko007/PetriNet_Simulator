package petriNet.petriNetObjects;

public final class Place extends Vertex {
    private int placeID;
    private String name;
    private int tokenCount;
    private static int placeCount = 0;

    public Place() {
        this.placeID = generateObjectID();
        this.name = "place" + placeCount;
        placeCount++;
    }

    public Place(int tokens) {
        this();
        this.tokenCount = tokens;

    }

    public Place(String name) {
        this();
        this.name = name;
    }

    public Place(String name, int tokens) {
        this();
        this.name = name;
        this.tokenCount = tokens;
    }

    public int getID() {
        return placeID;
    }

    public void setID(int placeID) {
        this.placeID = placeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTokens() {
        return tokenCount;
    }

    public void setTokens(int tokenCount) {
        this.tokenCount = tokenCount;
    }
}
