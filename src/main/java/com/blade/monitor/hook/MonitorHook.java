package com.blade.monitor.hook;

import com.blade.ioc.annotation.Bean;
import com.blade.mvc.hook.Signature;
import com.blade.mvc.hook.WebHook;
import com.blade.monitor.bootstrap.MonitorBootstrap;
import com.blade.monitor.model.RouteVO;

/**
 * @author biezhi
 * @date 2018/5/10
 */
@Bean
public class MonitorHook implements WebHook {

    @Override
    public boolean before(Signature signature) {
        return true;
    }

    @Override
    public boolean after(Signature signature) {

        String allPath = signature.getRoute().getAllPath();

        MonitorBootstrap.getRouteVOS().stream().filter(routeVO -> allPath.equals(routeVO.getAllPath()))
                .findFirst().ifPresent(RouteVO::addCount);

        return true;
    }
}
