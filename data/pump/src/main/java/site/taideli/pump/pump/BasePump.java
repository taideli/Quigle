package site.taideli.pump.pump;

import site.taideli.pump.base.Namedly;
import site.taideli.pump.parallel.OpenableThread;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

abstract class BasePump<V, P extends BasePump<V, P>> extends Namedly implements Pump {

    protected final String name;
    private final int parallelism;
    private final List<OpenableThread> tasks = new ArrayList<>();

    protected long batchSize = DEFAULT_BATCH_SIZE;
    protected final List<AutoCloseable> dependencies = new ArrayList<>();
    protected long forceTrace;

    public BasePump(String name, int parallelism) {
        super(name);
        this.name = name;
        this.parallelism = (parallelism > 0 ? parallelism : 16);
        forceTrace = batchSize / parallelism;
        logInfo(MessageFormat.format("Pump [{0}] created with parallelism [{1}]", name, parallelism));
    }
}
