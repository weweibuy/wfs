package com.weweibuy.wfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * web 前置项目
 *
 * @author durenhao
 * @date 2021/7/23 21:30
 **/
@SpringBootApplication
@EnableFeignClients
public class WfsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WfsApplication.class, args);
    }

}
