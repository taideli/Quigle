package site.taideli.pump.sink;

import site.taideli.pump.base.Adapter;

import java.util.function.Consumer;
import java.util.stream.Stream;

public interface Sink<V> extends Adapter, Consumer<Stream<V>>, Enqueue<V> {

    static Sink<?> NULL = items -> 0;

    @Override
    default long size() {
        return 0;
    }

    @Override
    default void accept(Stream<V> items) {
        enqueue(items);
    }
}