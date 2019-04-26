package site.taideli.pump.parallel;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 可并行处理的迭代器
 * @param <V>
 */
public class SplittableIterator<V> implements Spliterator<V> {

    private final int chars;
    private final ResPool<ReentrantLock>.Res lock;
    private final Supplier<V> get;
    private final Supplier<Boolean> ending;
    private long est;

    public SplittableIterator(Iterator<V> it) {
        this(it, Long.MAX_VALUE);
    }

    public SplittableIterator(Iterator<V> it, long size) {
        this(it::next, size, () -> !it.hasNext());
    }

    public SplittableIterator(Supplier<V> get, Supplier<Boolean> ending) {
        this(get, Long.MAX_VALUE, ending);
    }

    public SplittableIterator(Supplier<V> get, long size, Supplier<Boolean> ending) {
        super();
        est = size;
        int c = Spliterator.CONCURRENT | Spliterator.IMMUTABLE | Spliterator.ORDERED;
        if (!infinite() && est > 0) {
            c = c | Spliterator.SIZED | Spliterator.SUBSIZED;
        }
        chars = c;
        this.get = get;
        this.ending = ending;
        this.lock = ResPool.FAIR_LOCKERS.aquire();
    }

    /**
     * 是否是无尽的
     * @return true if infinite, else false
     */
    private boolean infinite() {
        return est == Long.MAX_VALUE;
    }

    @Override
    public boolean tryAdvance(Consumer<? super V> action) {
        if (!lock.res.tryLock()) return false;
        try {
            boolean hasNext = !ending.get();
            if (!hasNext) est = 0;
            else if (!infinite()) est--;
            hasNext = hasNext && est >= 0;
            if (hasNext) {
                V v = get.get();
                hasNext = null != v;
                if (hasNext) action.accept(v);
            }
            return hasNext;
        } finally {
            lock.res.unlock();
        }
    }

    @Override
    public Spliterator<V> trySplit() {
        if (infinite()) return new SplittableIterator<>(get, ending);
        if (est < 2) return null;
        lock.res.lock();
        try {
            long est1 = est / 2;
            return new SplittableIterator<>(get, est1, ending);
        } finally {
            lock.res.unlock();
        }
    }

    @Override
    public long estimateSize() {
        lock.res.lock();
        try {
            return est;
        } finally {
            lock.res.unlock();
        }
    }

    @Override
    public int characteristics() {
        return chars;
    }
}
