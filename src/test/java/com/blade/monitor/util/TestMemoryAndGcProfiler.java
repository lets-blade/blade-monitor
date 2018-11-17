package com.blade.monitor.util;



import com.blade.monitor.profilers.MemoryAndGcProfiler;
import java.util.ArrayList;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class TestMemoryAndGcProfiler {

    @Test
    public void testMemoryAndGcInfo() {
        ArrayList<Map<String, Object>> memoryAndGcInfo = new ArrayList<>();
        MemoryAndGcProfiler memoryAndGcProfiler = new MemoryAndGcProfiler((name, map) -> memoryAndGcInfo.add(map));
        memoryAndGcProfiler.profile();
        Assert.assertEquals(1, memoryAndGcInfo.size());
    }

}