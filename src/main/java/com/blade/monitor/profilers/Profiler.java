package com.blade.monitor.profilers;

/**
 * Profiler
 * @author PSH
 * Date: 2018/11/7
 */
public interface Profiler {

    /**
     *
     */
    void profile();

    /**
     * @param report  reSet report
     */
    void setReport(Reporter report);
}
