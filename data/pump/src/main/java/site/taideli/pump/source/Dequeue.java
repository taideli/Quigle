package site.taideli.pump.source;

import java.util.function.Function;
import java.util.stream.Stream;

@FunctionalInterface
public interface Dequeue<V> {

    long dequeue(Function<Stream<V>, Long> using, long batch);
}
