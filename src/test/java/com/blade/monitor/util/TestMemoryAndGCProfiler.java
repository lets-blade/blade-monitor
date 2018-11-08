package com.blade.monitor.util;



import com.blade.monitor.profilers.MemoryAndGCProfiler;
import java.util.ArrayList;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class TestMemoryAndGCProfiler {

    @Test
    public void testMemoryAndGcInfo() {
        ArrayList<Map<String, Object>> memoryAndGcInfo = new ArrayList<>();
        MemoryAndGCProfiler memoryAndGCProfiler = new MemoryAndGCProfiler((name, map) -> memoryAndGcInfo.add(map));
        memoryAndGCProfiler.profile();
        Assert.assertEquals(1, memoryAndGcInfo.size());
    }

}