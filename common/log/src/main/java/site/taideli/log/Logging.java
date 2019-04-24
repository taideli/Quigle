package site.taideli.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface Logging {

    default public void logTrace(String msg) {
        logTrace(msg, null);
    }

    default public void logTrace(String msg, Throwable cause) {
        if (null == cause) {
            logger().trace(msg);
        } else {
            logger().trace(msg, cause);
        }
    }

    default public void logDebug(String msg) {
        logDebug(msg, null);
    }

    default public void logDebug(String msg, Throwable cause) {
        if (null == cause) {
            logger().debug(msg);
        } else {
            logger().debug(msg, cause);
        }
    }

    default public void logInfo(String msg) {
        logInfo(msg, null);
    }

    default public void logInfo(String msg, Throwable cause) {
        if (null == cause) {
            logger().info(msg);
        } else {
            logger().info(msg, cause);
        }
    }

    default public void logWarn(String msg) {
        logWarn(msg, null);
    }

    default public void logWarn(String msg, Throwable cause) {
        if (null == cause) {
            logger().warn(msg);
        } else {
            logger().warn(msg, cause);
        }
    }

    default public void logError(String msg) {
        logError(msg, null);
    }

    default public void logError(String msg, Throwable cause) {
        if (null == cause) {
            logger().error(msg);
        } else {
            logger().error(msg, cause);
        }
    }

    default  Logger logger() {
        return Logged.LOGGERS.computeIfAbsent(this.getClass(), LoggerFactory::getLogger);
    }

    class Logged {
        private static final Map<Class<? extends Logging>, org.slf4j.Logger> LOGGERS = new ConcurrentHashMap<>();
    }


}

