package site.taideli.pump.sink;

import site.taideli.pump.base.Namedly;
import site.taideli.pump.parallel.Concurrents;
import site.taideli.pump.util.Streams;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public abstract class BaseSink<V> extends Namedly implements Sink<V> {

    protected BaseSink() {
        super();
    }

    protected BaseSink(String name) {
        super(name);
    }

    protected abstract boolean enqueue(V item);

    @Override
    public final long enqueue(Stream<V> items) {
        //
        if (!Concurrents.waitSleep(this::full)) return 0;
        AtomicLong count = new AtomicLong(0);
        Streams.of(items).forEach(item -> {
            if (enqueue(item)) count.incrementAndGet();
        });

        return count.get();
    }
}
