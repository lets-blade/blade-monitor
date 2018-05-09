package com.blade.plugin.monitor.controller;

import com.blade.mvc.WebContext;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.route.Route;
import com.blade.mvc.ui.RestResponse;
import com.blade.plugin.monitor.model.RouteVO;
import com.blade.plugin.monitor.model.SystemVO;
import com.blade.plugin.monitor.utils.CPUMonitorCalc;
import com.blade.plugin.monitor.utils.SystemUtils;

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

    @GetRoute("routes")
    public RestResponse routes() {
        List<RouteVO> routeVOS = this.getRoutes();
        return RestResponse.ok(routeVOS);
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

    private List<RouteVO> getRoutes() {
        Map<String, Route> routes = WebContext.blade().routeMatcher().getRoutes();

        return routes.values().stream()
                .map(route -> new RouteVO(route.getPath(), route.getHttpMethod().name()))
                .collect(Collectors.toList());
    }

}
