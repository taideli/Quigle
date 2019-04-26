package site.taideli.pump.util;

import sun.misc.Signal;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Systems extends Utils {

    public static String POOL_LOCK_SIZE_KEY = "pool.lock.size";
    public static String POOL_LOCK_FAIR_SIZE_KEY = "pool.lock.fair.size";

    private static final Map<String, List<Consumer<Signal>>> SIGNAL_HANDLERS = new ConcurrentHashMap<>();

    public static void handleSignal(Consumer<Signal> handler, String... signals) {
        for (String signal : signals) {
            SIGNAL_HANDLERS.computeIfAbsent(signal, s -> {
                Signal.handle(new Signal(s), sig -> {
                    List<Consumer<Signal>> handlers = SIGNAL_HANDLERS.get(sig.getName());
                    synchronized (handlers) {
                        System.out.println(MessageFormat.format("Signal [{}][{}] caught, [{}] handlers registered and will be invoking",
                                sig.getName(), sig.getNumber(), handlers.size()));
                        for (Consumer<Signal> h : handlers) h.accept(sig);
                    }
                });
                return new ArrayList<>();
            }).add(handler);

        }
    }
}
