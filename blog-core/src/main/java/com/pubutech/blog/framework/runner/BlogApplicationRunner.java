package com.pubutech.blog.framework.runner;

import com.pubutech.blog.framework.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class BlogApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments applicationArguments) {
        log.info("博客部署完成，当前时间：" + DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }
}
