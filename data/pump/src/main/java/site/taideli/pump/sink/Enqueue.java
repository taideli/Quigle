package site.taideli.pump.sink;

import java.util.stream.Stream;

@FunctionalInterface
public interface Enqueue<V> {

    long enqueue(Stream<V> items);
}
