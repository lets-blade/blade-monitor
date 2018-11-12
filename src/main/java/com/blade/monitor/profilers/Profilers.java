package com.blade.monitor.profilers;

/**
 * @author PSH
 * Date: 2018/11/12
 */
public class Profilers {

    /**
     * created a cpuProfiler that report nothing do
     * @return the newly created cpuProfiler
     */
    public static CpuProfiler newCpuProfiler  () {
        return newCpuProfiler((n, m) -> {});
    }

    /**
     *  create a cpuProfiler that impl report
     * @param reporter report impl
     * @return the newly created cpuProfiler
     */
    public static CpuProfiler newCpuProfiler  (Reporter reporter) {
       return new CpuProfiler(reporter);
    }

    /**
     * created a memoryAndGCProfiler that nothing do
     * @return
     */
    public static MemoryAndGcProfiler newMemoryAndGcProfiler  () {
       return new MemoryAndGcProfiler((n, m) -> {});
    }

    /**
     * created a memoryAndGCProfiler that impl report
     * @param reporter  report impl
     * @return
     */
    public static MemoryAndGcProfiler newMemoryAndGcProfiler  (Reporter reporter) {
       return new MemoryAndGcProfiler(reporter);
    }



}
