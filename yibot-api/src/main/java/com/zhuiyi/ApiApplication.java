package com.zhuiyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/13
 * description:
 * own: zhuiyi
 */

@SpringBootApplication
//@EnableCaching
//@ServletComponentScan
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
