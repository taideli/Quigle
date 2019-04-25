package site.taideli.pump.sink;

import site.taideli.pump.Wrapper;
import site.taideli.pump.base.Adapter;
import site.taideli.pump.util.Streams;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public interface Sink<V> extends Adapter, Consumer<Stream<V>>, Enqueue<V> {

    static Sink<?> NULL = items -> 0;

    default <V0> Sink<V0> prior(Function<V0, V> converter) {
//        return Wrapper.wrap(this, items -> enqueue(items.map(converter)));
        return Wrapper.wrap(this, items -> enqueue(Streams.of(items).map(converter)));
    }

    // TODO: add feature failover

    @Override
    default long size() {
        return 0;
    }

    @Override
    default void accept(Stream<V> items) {
        enqueue(items);
    }
}