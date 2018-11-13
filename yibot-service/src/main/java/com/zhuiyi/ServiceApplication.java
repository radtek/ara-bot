package com.zhuiyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/10
 * description:
 * own: zhuiyi
 */

@SpringBootApplication
@EnableCaching
public class ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}
