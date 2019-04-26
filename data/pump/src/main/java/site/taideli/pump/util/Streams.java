package site.taideli.pump.util;

import site.taideli.pump.parallel.SplittableIterator;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Streams {

    // TODO: 从配置中获取
    private static final boolean STREAM_PARALLEL_ENABLED = false;

    public static <V> Stream<V> of(Stream<V> stream) {
        if (STREAM_PARALLEL_ENABLED) stream = stream.parallel();
        return stream.filter(Objects::nonNull);
    }

    public static <V> Stream<V> of(Spliterator<V> sit) {
        return StreamSupport.stream(sit, STREAM_PARALLEL_ENABLED).filter(Objects::nonNull);
    }

    public static <V> Stream<V> of(Supplier<V> get, long size, Supplier<Boolean> ending) {
        return Streams.of(new SplittableIterator<>(get, size, ending));
    }
}
