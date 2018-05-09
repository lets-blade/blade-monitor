package com.blade.monitor.utils;

import com.blade.kit.ConvertKit;
import com.blade.monitor.model.SystemVO;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;

/**
 * @author biezhi
 * @date 2018/5/10
 */
public class SystemUtils {

    public static void setMemoryUsage(SystemVO systemVO) {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        long totalMemorySize = 0L;
        long freePhysicalMemorySize = 0L;
        long usedMemorySize = 0L;
        for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.getName().startsWith("get")
                    && Modifier.isPublic(method.getModifiers())) {
                Object value;
                try {
                    value = method.invoke(operatingSystemMXBean);
                } catch (Exception e) {
                    value = e;
                } // try
                if (method.getName().equals("getFreePhysicalMemorySize")) {
                    freePhysicalMemorySize = new BigDecimal(value.toString()).longValue();
                }
                if (method.getName().equals("getTotalPhysicalMemorySize")) {
                    totalMemorySize = new BigDecimal(value.toString()).longValue();
                }
            } // if
        } // for

        usedMemorySize = totalMemorySize - freePhysicalMemorySize;

        systemVO.setTotalMemory(ConvertKit.byte2FitMemorySize(totalMemorySize));
        systemVO.setFreePhysicalMemory(ConvertKit.byte2FitMemorySize(freePhysicalMemorySize));
        systemVO.setUsedMemory(ConvertKit.byte2FitMemorySize(usedMemorySize));
    }

}
