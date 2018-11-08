package com.blade.monitor.util;



import com.blade.monitor.profilers.CpuProfiler;
import java.util.ArrayList;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class TestCpuProfiler {

    @Test
    public void testCpuInfo() {
        ArrayList<Map<String, Object>> cpuAttributes = new ArrayList<>();
        CpuProfiler cpuProfiler = new CpuProfiler((profilerName, metricInfo) -> cpuAttributes.add(metricInfo));
        cpuProfiler.profile();
        Assert.assertEquals(1, cpuAttributes.size());
    }

}