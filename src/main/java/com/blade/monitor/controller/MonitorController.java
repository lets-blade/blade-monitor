package com.blade.monitor.controller;

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

    @GetRoute("/system_more")
    public String systemMore(Response response) {
        return "jar:/templates/system_more.html";
    }


}