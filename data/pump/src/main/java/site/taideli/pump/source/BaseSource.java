package site.taideli.pump.source;

import site.taideli.pump.base.Namedly;
import site.taideli.pump.util.Streams;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class BaseSource<V> extends Namedly implements Source<V>, Supplier<V>, Iterator<V> {

    protected BaseSource() {
        super();
    }

    protected BaseSource(String name) {
        super(name);
    }

    protected abstract V dequeue();

    @Override
    public final long dequeue(Function<Stream<V>, Long> using, long batch) {
        return using.apply(Streams.of(this::dequeue, batch, () -> empty() && opened()));
    }
}
