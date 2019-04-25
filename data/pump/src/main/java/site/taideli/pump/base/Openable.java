package site.taideli.pump.base;

import site.taideli.pump.parallel.Concurrents;
import site.taideli.log.Loggable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public interface Openable extends AutoCloseable, Loggable, Named {

    enum Status {
        OPENING, OPENED, CLOSING, CLOSED
    }

    class Opened {
        private static final Map<Openable, AtomicReference<Status>> STATUS = new ConcurrentHashMap<>();
        private static final Map<Openable, Runnable> OPENING = new ConcurrentHashMap<>(),
                                                     CLOSING = new ConcurrentHashMap<>();

        private Opened() {}

        private static AtomicReference<Status> status(Openable inst) {
            return STATUS.getOrDefault(inst, new AtomicReference<>(Status.CLOSED));
        }

    }

    default boolean opened() {
        return Opened.status(this).get() == Status.OPENED;
    }

    default boolean closed() {
        return Opened.status(this).get() == Status.CLOSED;
    }

    default void opening(Runnable handler) {
        Opened.OPENING.compute(this,
                (self, orig) -> null == orig ? handler : Runnable.merge(orig, handler));
    }

    default void closing(Runnable handler) {
        Opened.CLOSING.compute(this,
                (self, orig) -> null == orig ? handler : Runnable.merge(orig, handler));
    }

    default void open() {
        AtomicReference<Status> s = Opened.STATUS.computeIfAbsent(this,
                o -> new AtomicReference<>(Status.CLOSED));
        if (s.compareAndSet(Status.CLOSED, Status.OPENING)) {
            logger().info(name() + " opening...");
            Runnable h = Opened.OPENING.get(this);
            if (null != h) h.run();
            if (!s.compareAndSet(Status.OPENING, Status.OPENED)) {
                throw new RuntimeException(String.format("Opened failure since status [%s] not OPENING.", s.get()));
            }
        }

        if (Status.OPENED != s.get()) {
            throw new RuntimeException(String.format("Start failure since status [%s] not OPENED.", s.get()));
        }
        logger().info(name() + " opened");
    }

    @Override
    default void close() {
        AtomicReference<Status> s = Opened.status(this);
        if (s.compareAndSet(Status.OPENED, Status.CLOSING)) {
            logger().info(name() + " closing...");
            Runnable h = Opened.CLOSING.get(this);
            if (null != h) h.run();
            s.compareAndSet(Status.CLOSING, Status.CLOSED);
        }

        while (!closed()) {
            Concurrents.waitSleep(500, this, "Waiting for closing finished....");
            Opened.STATUS.remove(this);

        }
        logger().info(name() + " closed");
    }
}
