package site.taideli.pump.source;

import site.taideli.pump.base.Adapter;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public interface Source<V> extends Adapter, Dequeue<V>, Supplier<V>, Iterator<V> {


    @Override
    default long size() {
        return Long.MAX_VALUE;
    }

    @Override
    default long capacity() {
        return 0;
    }

    @Override
    default V get() {
        AtomicReference<V> ref = new AtomicReference<>();
        dequeue(s -> {
            ref.lazySet(s.findFirst().orElse(null));
            return 1L;
        }, 1);
        return ref.get();
    }

    @Override
    default boolean hasNext() {
        return !empty();
    }

    @Override
    default V next() {
        return get();
    }
}