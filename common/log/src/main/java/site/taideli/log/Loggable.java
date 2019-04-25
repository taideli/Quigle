package site.taideli.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface Loggable {
    
    default Logger logger() {
        return LogImpl.LOGGERS.computeIfAbsent(getClass(), LoggerFactory::getLogger);
    }

    default void logTrace(String msg) {
        logTrace(msg, null);
    }

    default void logTrace(String msg, Throwable cause) {
        if (logger().isTraceEnabled()) {
            if (null == cause) logger().trace(msg); else logger().trace(msg, cause);
        }
    }

    default void logDebug(String msg) {
        logDebug(msg, null);
    }

    default void logDebug(String msg, Throwable cause) {
        if (logger().isDebugEnabled()) {
            if (null == cause) logger().debug(msg); else logger().debug(msg, cause);
        }
    }

    default void logInfo(String msg) {
        logInfo(msg, null);
    }

    default void logInfo(String msg, Throwable cause) {
        if (logger().isInfoEnabled()) {
            if (null == cause) logger().info(msg); else logger().info(msg, cause);
        }
    }

    default void logWarning(String msg) {
        logWarning(msg, null);
    }

    default void logWarning(String msg, Throwable cause) {
        if (logger().isWarnEnabled()) {
            if (null == cause) logger().warn(msg); else logger().warn(msg, cause);
        }
    }

    default void logError(String msg) {
        logError(msg, null);
    }

    default void logError(String msg, Throwable cause) {
        if (logger().isErrorEnabled()) {
            if (null == cause) logger().error(msg); else logger().error(msg, cause);
        }
    }

    static class LogImpl {
        static final Map<Class<?>, org.slf4j.Logger> LOGGERS = new ConcurrentHashMap<>();
    }
    
}
