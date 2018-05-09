package com.blade.plugin.monitor.controller;

import com.blade.mvc.WebContext;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.route.Route;
import com.blade.mvc.ui.RestResponse;
import com.blade.plugin.monitor.model.RouteVO;

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

    private List<RouteVO> getRoutes() {
        Map<String, Route> routes = WebContext.blade().routeMatcher().getRoutes();

        return routes.values().stream()
                .map(route -> new RouteVO(route.getPath(), route.getHttpMethod().name()))
                .collect(Collectors.toList());
    }

}
