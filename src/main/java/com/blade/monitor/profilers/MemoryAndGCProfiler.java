package com.blade.monitor.profilers;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author PSH
 * Date: 2018/11/8
 */
public class MemoryAndGCProfiler implements Profiler {

    private Reporter            reporter;

    private MemoryMXBean        memoryMXBean;

    public MemoryAndGCProfiler (Reporter reporter) {
        this.reporter = reporter;
        init ();
    }

    /**
     * init mBeans
     */
    private void init () {
       this.memoryMXBean = ManagementFactory.getMemoryMXBean();
    }

    @Override
    public void profile() {

        HashMap<String, Object> memoryMap = new HashMap<>(5);
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        Long heapMemoryTotalUsed = heapMemoryUsage.getUsed();
        Long heapMemoryCommitted= heapMemoryUsage.getCommitted();

        memoryMap.put("heapMemoryTotalUsed", heapMemoryTotalUsed);
        memoryMap.put("heapMemoryCommitted", heapMemoryCommitted);

        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        if (garbageCollectorMXBeans != null) {
            List<HashMap<String, Object>> collect = garbageCollectorMXBeans.stream()
                .map(gcMxBean -> {
                    HashMap<String, Object> gcInfo = new HashMap<>();
                    String name = gcMxBean.getName();
                    gcInfo.put("name", name);
                    gcInfo.put("collectionCount", gcMxBean.getCollectionCount());
                    gcInfo.put("collectionTime", gcMxBean.getCollectionTime());
                    return gcInfo;

                })
                .collect(Collectors.toList());
            memoryMap.put("gc", collect);
        }

        reporter.report("MemoryAndGC", memoryMap);
    }

    @Override
    public void setReport(Reporter report) {
        this.reporter = report;
    }
}
