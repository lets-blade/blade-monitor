package com.blade.monitor.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.math.BigDecimal;

public class CPUMonitorCalc {

    private static CPUMonitorCalc instance = new CPUMonitorCalc();

    private OperatingSystemMXBean osMxBean;
    private ThreadMXBean          threadBean;
    private long                  preTime     = System.nanoTime();
    private long                  preUsedTime = 0;

    private CPUMonitorCalc() {
        osMxBean = ManagementFactory.getOperatingSystemMXBean();
        threadBean = ManagementFactory.getThreadMXBean();
    }

    public static CPUMonitorCalc getInstance() {
        return instance;
    }

    public double getProcessCpu() {
        long totalTime = 0;
        for (long id : threadBean.getAllThreadIds()) {
            totalTime += threadBean.getThreadCpuTime(id);
        }
        long curtime         = System.nanoTime();
        long usedTime        = totalTime - preUsedTime;
        long totalPassedTime = curtime - preTime;
        preTime = curtime;
        preUsedTime = totalTime;
        return new BigDecimal((((double) usedTime) / totalPassedTime / osMxBean.getAvailableProcessors()) * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
