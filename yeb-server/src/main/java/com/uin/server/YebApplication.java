package com.uin.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 *
 * @author wanglufei
 * @date 2022/4/15 1:22 PM
 */
@SpringBootApplication
@MapperScan("com.uin.server.mapper")
/**
 * 开启定时任务
 */
@EnableScheduling
public class YebApplication {
    public static void main(String[] args) {
        SpringApplication.run(YebApplication.class,args);
    }
}
