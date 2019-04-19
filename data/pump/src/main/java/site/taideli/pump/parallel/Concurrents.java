package site.taideli.pump.parallel;

public class Concurrents {

    public static long DEFAULT_WAIT_MS = 100;

    public static boolean waitSleep() {
        return waitSleep(DEFAULT_WAIT_MS);
    }

    public static boolean waitSleep(long millis) {
        return waitSleep(millis, null, null);
    }

    public static boolean waitSleep(long millis, Logger logger, CharSequence cause) {
        if (millis < 0) return true;
        try {
            if (null != logger && logger.isTraceEnabled()) {
                logger.trace(String.format("Thread [%s] sleep %dms, cause [%s]", Thread.currentThread().getName(), millis, cause));
                Thread.sleep(millis);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
