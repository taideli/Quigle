package site.taideli.pump.base;

public interface Named {

    default String name() {
        return getClass().getSimpleName();
    }
}
