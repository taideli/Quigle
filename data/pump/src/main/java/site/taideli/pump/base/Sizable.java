package site.taideli.pump.base;

public interface Sizable {

    long size();

    default long capacity() {
        return Long.MAX_VALUE;
    }

    default boolean empty() {
        return size() <= 0;
    }

    default boolean full() {
        return size() >= capacity();
    }
}
