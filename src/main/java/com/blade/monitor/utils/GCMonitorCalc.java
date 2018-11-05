package com.blade.monitor.utils;


import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import lombok.extern.slf4j.Slf4j;

/**
 * @author PSH
 * Date: 2018/11/5
 */
@Slf4j
public class GCMonitorCalc {

    /**
     * MemoryMXBean
     */
    private MemoryMXBean memoryMXBean;

    public GCMonitorCalc () {
        init();
    }

    public Double getMenoryProfile () {
        if (memoryMXBean != null) {
            memoryMXBean.getHeapMemoryUsage();
        }
        Double heapMemoryTotalUsed = null;
        return null;
    }

    private void init () {
        try {
            memoryMXBean = ManagementFactory.getMemoryMXBean();
        } catch (Throwable throwable){
            log.warn("Failed to get MemoryMXBean: ", throwable);
        }
    }
}
