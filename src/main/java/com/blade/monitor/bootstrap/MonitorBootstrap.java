package com.blade.monitor.bootstrap;

import com.blade.Blade;
import com.blade.event.BeanProcessor;
import com.blade.ioc.annotation.Bean;
import com.blade.monitor.model.RouteVO;
import com.blade.mvc.WebContext;
import com.blade.mvc.route.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author biezhi
 * @date 2018/5/10
 */
@Bean
public class MonitorBootstrap implements BeanProcessor {

    private static List<RouteVO> routeVOS = new ArrayList<>();

    @Override
    public void processor(Blade blade) {
        Map<String, Route> routes = WebContext.blade().routeMatcher().getRoutes();
        routeVOS = routes.values().stream()
                .filter(route -> !route.getPath().startsWith("/monitor"))
                .map(route -> new RouteVO(route.getAllPath(), route.getPath(), route.getHttpMethod().name()))
                .collect(Collectors.toList());
    }

    public static List<RouteVO> getRouteVOS() {
        return routeVOS;
    }

}
