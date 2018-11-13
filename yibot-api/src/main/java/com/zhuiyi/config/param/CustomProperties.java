package com.zhuiyi.config.param;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: tree
 * @version: 1.0
 * date: 2018/6/19 11:52
 * @description: 系统配置类
 * own:
 */
@Component
@Data
@ConfigurationProperties(prefix = "yibot")
public class CustomProperties {
    private String suiTitle;
    private String description;
    private String version;
    private String basePackage;
    private String allowAppKeys;
    private String oldServiceUrl;
}
