package site.taideli.pump.parallel;

import site.taideli.log.Loggable;

import java.util.function.Supplier;

public class Concurrents {

    public static long DEFAULT_WAIT_MS = 100;

    public static boolean waitSleep(Supplier<Boolean> waiting) {
        while (waiting.get()) {
            if (!Concurrents.waitSleep()) return false;
        }
        return true;
    }

    public static boolean waitSleep() {
        return waitSleep(DEFAULT_WAIT_MS);
    }

    public static boolean waitSleep(long millis) {
        return waitSleep(millis, null, null);
    }

    public static boolean waitSleep(long millis, Loggable logger, CharSequence cause) {
        if (millis < 0) return true;
        try {
            if (null != logger) {
                logger.logDebug(String.format("Thread [%s] sleep %dms, cause [%s]", Thread.currentThread().getName(), millis, cause));
            }
            Thread.sleep(millis);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
