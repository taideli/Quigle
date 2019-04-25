package site.taideli.pump.util;

import java.util.Objects;
import java.util.stream.Stream;

public class Streams {

    // TODO: 从配置中获取
    private static final boolean STREAM_PARALLEL_ENABLED = false;

    public static <V> Stream<V> of(Stream<V> stream) {
        if (STREAM_PARALLEL_ENABLED) stream = stream.parallel();
        return stream.filter(Objects::nonNull);
    }
}
