package com.blade.monitor.model;

import com.blade.kit.json.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author biezhi
 * @date 2018/5/9
 */
@Data
@NoArgsConstructor
public class RouteVO {

    private String path;
    private String method;
    private long   invokeCount;

    @JsonIgnore
    private AtomicLong atomicLong = new AtomicLong(0);
    private String     allPath;

    public RouteVO(String allPath, String path, String method) {
        this.allPath = allPath;
        this.path = path;
        this.method = method;
    }

    public void addCount() {
        invokeCount = atomicLong.incrementAndGet();
    }

}
