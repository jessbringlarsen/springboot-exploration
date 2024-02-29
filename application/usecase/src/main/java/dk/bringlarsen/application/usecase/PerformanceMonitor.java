package dk.bringlarsen.application.usecase;

import org.slf4j.Logger;

import java.util.function.Supplier;

class PerformanceMonitor<R> {

    R intercept(Logger logger, Supplier<R> method) {
        long startTime = System.currentTimeMillis();
        try {
            return method.get();
        } finally {
            long endTime = System.currentTimeMillis();
            logger.trace("execution_time_in_ms={}", (endTime - startTime));
        }
    }
}
