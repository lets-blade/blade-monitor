package com.blade.plugin.monitor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author biezhi
 * @date 2018/5/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteVO {

    private String path;
    private String method;

}
