package com.blade.plugin.monitor.controller;

import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.http.Response;

/**
 * @author biezhi
 * @date 2018/5/9
 */
@Path("monitor")
public class MonitorController {

    @GetRoute("/")
    public String index(Response response) {
        return "jar:/templates/monitor.html";
    }

}