package site.taideli.pump.base;

public abstract class Namedly implements Named {

    protected String name;

    public Namedly() {
        super();
        this.name = Named.super.name();
    }

    public Namedly(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name() + "#" + Integer.toHexString(hashCode());
    }
}
