package site.taideli.pump.parallel;

import site.taideli.pump.util.Systems;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public class ResPool<R> {
    private final BlockingQueue<R> pool;
    private final Supplier<R> constructor;

    public ResPool(int capability, Supplier<R> constructor) {
        pool = new LinkedBlockingQueue<>(capability);
        this.constructor = constructor;
    }

    public Res aquire() {
        return new Res();
    }

    private static int lockSize() {
        try {
            return Integer.parseInt(System.getProperty(Systems.POOL_LOCK_SIZE_KEY));
        } catch (Exception e) {
            return 100;
        }
    }

    private static int fairLockSize() {
        try {
            return Integer.parseInt(System.getProperty(Systems.POOL_LOCK_FAIR_SIZE_KEY));
        } catch (Exception e) {
            return 100;
        }
    }


    public static final ResPool<ReentrantReadWriteLock> RW_LOCKES = new ResPool<>(lockSize(), () -> new ReentrantReadWriteLock());
    public static final ResPool<ReentrantReadWriteLock> LOCKES_FAIR = new ResPool<>(fairLockSize(), () -> new ReentrantReadWriteLock(true));
    public static final ResPool<ReentrantLock>  FAIR_LOCKERS = new ResPool<>(100, () -> new ReentrantLock(true));

    public class Res implements AutoCloseable {
        public final R res;

        public Res() {
            R r = pool.poll();
            res = null == r ? constructor.get() : r;
        }

        @Override
        public void close() throws Exception {
            pool.offer(res);
        }
    }
}
