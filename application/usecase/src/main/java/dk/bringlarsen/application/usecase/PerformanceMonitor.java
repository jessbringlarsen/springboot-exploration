package dk.bringlarsen.application.usecase;

import org.slf4j.Logger;
import org.springframework.util.StopWatch;

import java.util.function.Supplier;

class PerformanceMonitor<R> {

    R intercept(Logger logger, Supplier<R> method) {
        StopWatch stopWatch = new StopWatch("execute");
        stopWatch.start();
        try {
            return method.get();
        } finally {
            stopWatch.stop();
            logger.trace(stopWatch.shortSummary());
        }
    }
}
