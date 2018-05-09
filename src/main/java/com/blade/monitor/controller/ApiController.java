package com.blade.monitor.controller;

import com.blade.monitor.bootstrap.MonitorBootstrap;
import com.blade.monitor.model.TaskVO;
import com.blade.monitor.utils.CPUMonitorCalc;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.ui.RestResponse;
import com.blade.monitor.model.SystemVO;
import com.blade.monitor.utils.SystemUtils;
import com.blade.task.TaskManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Api Controller
 *
 * @author biezhi
 * @date 2018/5/9
 */
@Path(value = "monitor/api", suffix = ".json", restful = true)
public class ApiController {

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

}
