package com.ust.shbay.manager.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Slf4j
@Component
public class StartService implements ApplicationRunner {

    @Value("${jobSwitch}")
    private String jobSwitch;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("Manager应用服务已启动 StartService run at {}", new Date());

        log.info("全局定时任务开关：{}", jobSwitch);
        //判断全局定时任务是否开启
        if(!StringUtils.isEmpty(jobSwitch) && jobSwitch.equals("true")){
            //开启定时任务


        }
    }
}
