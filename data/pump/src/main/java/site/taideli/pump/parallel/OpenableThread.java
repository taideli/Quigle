package site.taideli.pump.parallel;

import site.taideli.pump.base.Openable;
import site.taideli.pump.base.Runnable;

import java.util.concurrent.atomic.AtomicInteger;

public class OpenableThread extends Thread implements Openable {

    private static final int INITIALIZE = 0, RUNNING = 1, STOPPED = 2;

    private final AtomicInteger status = new AtomicInteger(INITIALIZE);
    private static final ThreadGroup group = new ThreadGroup("workers");

    public OpenableThread(String name) {
        super(group, name);
        init();
    }

    private void init() {
        setUncaughtExceptionHandler((t, e) -> logError(name() + " failure", e));
        opening(super::start);
    }

    public OpenableThread(Runnable r, String name) {
        super(group, r, name);
        init();
    }

    @Override
    public void run() {
        status.set(RUNNING);
        try {
            super.run();
        } finally {
            status.set(STOPPED);
        }
    }

    @Override
    public void open() {
        Openable.super.open();
        while (status.get() != INITIALIZE) {
            Concurrents.waitSleep(10);
        }
    }

    @Override
    public synchronized void start() {
        open();
    }

    @Override
    public String name() {
        return getName();
    }
}
