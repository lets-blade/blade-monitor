package com.blade.monitor.profilers;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import lombok.extern.slf4j.Slf4j;

/**
 * @author PSH
 * Date: 2018/11/7
 */
@Slf4j
public class CpuProfiler implements Profiler {

    private static final String PROFILER_NAME = "CPU";

    private static final String PROCESS_CPU_LOAD = "ProcessCpuLoad";
    private static final int INDEX_PROCESS_CPU_LOAD = 0;

    private static final String SYSTEM_CPU_LOAD = "SystemCpuLoad";
    private static final int INDEX_SYSTEM_CPU_LOAD = 1;

    private static final String PROCESS_CPU_TIME = "ProcessCpuTime";
    private static final int INDEX_PROCESS_CPU_TIME = 2;

    private Reporter    reporter;
    private MBeanServer platformMBeanServer;
    private ObjectName  systemObjectName;

    public CpuProfiler (Reporter reporter) {
        this.reporter = reporter;
        init ();
    }

    /**
     * init mBeans
     */
    private void init () {

        platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            systemObjectName = ObjectName.getInstance("java.lang:type=OperatingSystem");
        } catch (MalformedObjectNameException e) {
            log.warn("failed to get OperatingSystem MBean");
        }
    }

    @Override
    public synchronized void  profile() {
        if (reporter == null) {
            throw new NullPointerException("report is null");
        }
        HashMap<String, Object> attributes = new HashMap<>(3);
        reporter.report(PROFILER_NAME, attributes);


        AttributeList cpuAttributeList = getCpuAttributeList();
        if (cpuAttributeList == null || cpuAttributeList.size() == 0) {
            return;
        }

        Attribute attr = (Attribute) cpuAttributeList.get(INDEX_PROCESS_CPU_LOAD);
        Double processCpuLoad = (Double) attr.getValue();

        attr = (Attribute) cpuAttributeList.get(INDEX_SYSTEM_CPU_LOAD);
        Double systemCpuLoad = (Double) attr.getValue();

        attr = (Attribute) cpuAttributeList.get(INDEX_PROCESS_CPU_TIME);

        Long processCpuTime = (Long) attr.getValue();

        attributes.put("processCpuLoad", processCpuLoad);
        attributes.put("systemCpuLoad", systemCpuLoad);
        attributes.put("processCpuTime", processCpuTime);

    }

    @Override
    public void setReport(Reporter report) {

        this.reporter = report;
    }

    private AttributeList getCpuAttributeList() {
        String[] attributeNames = new String[]{PROCESS_CPU_LOAD, SYSTEM_CPU_LOAD, PROCESS_CPU_TIME};
        try {
            return platformMBeanServer.getAttributes(systemObjectName, attributeNames);
        } catch (Exception  e) {
            log.warn("failed to get cpu mBean attributes");
            return null;
        }
    }

}
