package site.taideli.pump.source;

import site.taideli.pump.base.Adapter;

import java.util.Iterator;
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

}