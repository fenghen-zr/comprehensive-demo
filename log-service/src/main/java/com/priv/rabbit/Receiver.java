package com.priv.rabbit;


import com.alibaba.fastjson.JSON;
import com.priv.entity.SysLog;
import com.priv.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;


/**
 * @author fenghen
 */
@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    SysLogService sysLogService;

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        SysLog  sysLog=  JSON.parseObject(message,SysLog.class);
        sysLogService.saveLogger(sysLog);
        latch.countDown();
    }


}