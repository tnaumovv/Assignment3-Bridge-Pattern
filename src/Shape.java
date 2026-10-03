public abstract class Shape {
    private final String id;
    private Renderer implementation;

    protected Shape(String id, Renderer implementation) {
        this.id = id;
        this.implementation = implementation;
    }

    public String getId() {
        return id;
    }

    protected Renderer getImplementation() {
        return implementation;
    }

    public void setImplementation(Renderer implementation) {
        this.implementation = implementation;
    }

    public abstract String execute();
}
