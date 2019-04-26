package site.taideli.pump.pump;

import site.taideli.pump.base.Openable;
import site.taideli.pump.util.Systems;

public interface Pump<V> extends /*Statistical<Pump<V>>,*/ Openable {

    Pump<V> batch(long batch);

    @Override
    default void open() {
        Openable.super.open();
        Systems.handleSignal(sig -> {

        }, "TERM", "INT");
    }
}
