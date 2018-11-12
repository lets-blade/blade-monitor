package com.blade.monitor.controller;

import com.blade.monitor.bootstrap.MonitorBootstrap;
import com.blade.monitor.model.TaskVO;
import com.blade.monitor.profilers.CpuProfiler;
import com.blade.monitor.profilers.MemoryAndGcProfiler;
import com.blade.monitor.profilers.Profilers;
import com.blade.monitor.utils.CPUMonitorCalc;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.ui.RestResponse;
import com.blade.monitor.model.SystemVO;
import com.blade.monitor.utils.SystemUtils;
import com.blade.task.TaskManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Api Controller
 *
 * @author biezhi
 * @date 2018/5/9
 */
@Path(value = "monitor/api", suffix = ".json", restful = true)
public class ApiController {
    public static CpuProfiler cpuProfiler = Profilers.newCpuProfiler();
    public static MemoryAndGcProfiler memoryAndGcProfiler = Profilers.newMemoryAndGcProfiler();

    @GetRoute("routes")
    public RestResponse routes() {
        return RestResponse.ok(MonitorBootstrap.getRouteVOS());
    }

    @GetRoute("sysInfo")
    public RestResponse sysInfo() {
        SystemVO systemVO = new SystemVO();
        systemVO.setJdkVersion(System.getProperty("java.version"));
        systemVO.setOsName(System.getProperty("os.name"));
        systemVO.setOsVersion(System.getProperty("os.version"));
        systemVO.setCpuRatio(CPUMonitorCalc.getInstance().getProcessCpu());

        ThreadGroup parentThread;
        for (parentThread = Thread.currentThread().getThreadGroup(); parentThread
                .getParent() != null; parentThread = parentThread.getParent())
            ;
        int totalThread = parentThread.activeCount();
        systemVO.setTotalThread(totalThread);

        SystemUtils.setMemoryUsage(systemVO);
        return RestResponse.ok(systemVO);
    }

    @GetRoute("tasks")
    public RestResponse tasks() {
        List<TaskVO> collect = TaskManager.getTasks().stream()
                .map(task -> new TaskVO(task.getName(), task.getCronExpression().getCronExpression(), task.isRunning()))
                .collect(Collectors.toList());
        return RestResponse.ok(collect);
    }

    @GetRoute("memoryandgc")
    public RestResponse getMemoryAndGcInfo (){
        final Map<String, Object> memoryAndGcInfo = new HashMap<>(5);
        memoryAndGcProfiler.setReport((name, map) -> memoryAndGcInfo.putAll(map));
        memoryAndGcProfiler.profile();
        return RestResponse.ok(memoryAndGcInfo);
    }

    @GetRoute("cpuinfo")
    public RestResponse getCpuInfo (){
        final Map<String, Object> cpuInfo = new HashMap<>(3);
        cpuProfiler.setReport((name, map) -> cpuInfo.putAll(map));
        cpuProfiler.profile();
        return RestResponse.ok(cpuInfo);
    }

}
