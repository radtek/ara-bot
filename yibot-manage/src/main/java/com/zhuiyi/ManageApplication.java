package com.zhuiyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/13
 * description:
 * own: zhuiyi
 */

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class ManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class, args);
    }


}
