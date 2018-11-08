package com.blade.monitor.profilers;

import java.util.Map;

/**
 * @author PSH
 * Date: 2018/11/7
 */

@FunctionalInterface
public interface Reporter {
    void report(String profilerName, Map<String, Object> metricInfo);
}
