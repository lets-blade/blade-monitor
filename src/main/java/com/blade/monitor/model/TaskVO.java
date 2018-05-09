package com.blade.monitor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author biezhi
 * @date 2018/5/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskVO {

    private String  name;
    private String  cron;
    private Boolean isRunning;

}
