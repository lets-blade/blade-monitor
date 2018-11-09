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
public class MemoryAndGcProfiler implements Profiler {

    private Reporter            reporter;

    private MemoryMXBean        memoryMXBean;

    public MemoryAndGcProfiler(Reporter reporter) {
        this.reporter = reporter;
        init ();
    }

    /**
     * init mBeans
     */
    private void init () {
       this.memoryMXBean = ManagementFactory.getMemoryMXBean();
    }

    /**
     *
     * this will get MemoryInfo and garbageCollectorInfo
     */
    @Override
    public synchronized void profile() {

        HashMap<String, Object> memoryMap = new HashMap<>(5);
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        Long heapMemoryTotalUsed = heapMemoryUsage.getUsed();
        Long heapMemoryCommitted= heapMemoryUsage.getCommitted();

        memoryMap.put("heapMemoryTotalUsed", heapMemoryTotalUsed / 1024 / 1024);
        memoryMap.put("heapMemoryCommitted", heapMemoryCommitted / 1024 / 1024);

        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        Long used = nonHeapMemoryUsage.getUsed();
        Long nonHeapMemoryCommitted = nonHeapMemoryUsage.getCommitted();
        memoryMap.put("nonHeapMemoryUsage", used);
        memoryMap.put("nonHeadMemoryCommitted", nonHeapMemoryCommitted);

        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        if (garbageCollectorMXBeans != null) {
            List<HashMap<String, Object>> garbageCollect = garbageCollectorMXBeans.stream()
                .map(gcMxBean -> {
                    HashMap<String, Object> gcInfo = new HashMap<>(3);
                    String name = gcMxBean.getName();
                    gcInfo.put("garbageCollectorName", name);
                    gcInfo.put("collectionCount", gcMxBean.getCollectionCount());
                    gcInfo.put("collectionTime", gcMxBean.getCollectionTime());
                    return gcInfo;

                })
                .collect(Collectors.toList());
            memoryMap.put("gc", garbageCollect);
        }

        //
        List<HashMap<String, Object>> pollCollect = ManagementFactory.getMemoryPoolMXBeans()
            .stream()
            .map(memoryPoolMXBean -> {
                HashMap<String, Object> pools = new HashMap<>(2);
                String name = memoryPoolMXBean.getName();
                MemoryUsage usage = memoryPoolMXBean.getUsage();
                pools.put("poolName", name);
                pools.put("usage", usage);
                return pools;
            }).collect(Collectors.toList());
        memoryMap.put("poolName", pollCollect);

        reporter.report("MemoryAndGC", memoryMap);
    }

    @Override
    public void setReport(Reporter report) {
        this.reporter = report;
    }
}
