package com.blade.monitor.model;

import lombok.Data;

/**
 * @author biezhi
 * @date 2018/5/10
 */
@Data
public class SystemVO {

    /**
     * OS Name
     */
    private String osName;

    /**
     * OS Version
     */
    private String osVersion;

    /**
     * JDK Version
     */
    private String jdkVersion;

    /**
     * CPU usage rate
     */
    private double cpuRatio;

    /**
     * Total physical memory
     */
    private String totalMemory;

    /**
     * The remaining physical memory
     */
    private String freePhysicalMemory;

    /**
     * Used physical memory
     */
    private String usedMemory;

    /**
     * Total thread
     */
    private int totalThread;

}
