package site.taideli.pump.base;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Runnable extends java.lang.Runnable {

    @Override
    void run();

    default Runnable prior(java.lang.Runnable action) {
        return () -> {
            action.run();
            run();
        };
    }

    default Runnable then(java.lang.Runnable action) {
        return () -> {
            run();
            action.run();
        };
    }

    // TODO: 不要用while等待，这样太消耗资源
    default Runnable until(Supplier<Boolean> condition) {
        return () -> {
            while (!condition.get()) {
                this.run();
            }
        };
    }

    default Runnable exception(Consumer<Exception> handler) {
        return () -> {
            try {
                run();
            } catch (Exception e) {
                handler.accept(e);
            }
        };
    }

    static Runnable merge(java.lang.Runnable... actions) {
        return () -> {
            for (java.lang.Runnable action : actions) {
                if (null != action) action.run();
            }
        };
    }

    // TODO: 不要用while等待，这样太消耗资源
    static Runnable until(java.lang.Runnable action, Supplier<Boolean> condition) {
        return () -> {
            while (!condition.get()) {
                action.run();
            }
        };
    }

    static Runnable exception(java.lang.Runnable action, Consumer<Exception> handler) {
        return () -> {
            try {
                action.run();
            } catch (Exception e) {
                handler.accept(e);
            }
        };
    }
}
